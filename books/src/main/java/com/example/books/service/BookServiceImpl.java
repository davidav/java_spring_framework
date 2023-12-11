package com.example.books.service;

import com.example.books.config.properties.AppCacheProperties;
import com.example.books.dto.BookListResponse;
import com.example.books.dto.BookResponse;
import com.example.books.dto.BookMapper;
import com.example.books.model.Book;
import com.example.books.model.Category;
import com.example.books.repo.BookRepository;
import com.example.books.repo.CategoryRepository;
import com.example.books.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManager")
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Cacheable(value = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, key = "#title + #author")
    public BookResponse findByTitleAndAuthor(String title, String author) {
        return bookMapper.bookToResponse(bookRepository.findByTitleAndAuthor(title, author)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("book title-{} author-{} not found", title, author).getMessage())));
    }

    @Override
    @Cacheable(value = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME, key = "#categoryName")
    public BookListResponse findAllByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("category name-{} not found", categoryName).getMessage()));

        return bookMapper.bookListToBookListResponse(bookRepository.findAllByCategory(category));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, key = "#title + #author", beforeInvocation = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME, key = "#categoryName", beforeInvocation = true)
    })
    public BookResponse create(String title, String author, String categoryName) {
        Book newBook = Book.builder()
                .title(title)
                .author(author)
                .category(getCategory(categoryName))
                .build();
        newBook.getCategory().addBook(newBook);

        return bookMapper.bookToResponse(bookRepository.save(newBook));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, key = "#title + #author", beforeInvocation = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME, key = "#categoryName", beforeInvocation = true)
    })
    public BookResponse update(Long id, String title, String author, String categoryName) {
        Book existedBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("book id-{} not found", id).getMessage()));
        Book updateBook = Book.builder()
                .title(title)
                .author(author)
                .category(getCategory(categoryName))
                .build();
        AppHelperUtils.copyNonNullProperties(updateBook, existedBook);

        return bookMapper.bookToResponse(bookRepository.save(existedBook));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOK_BY_TITLE_AND_AUTHOR, allEntries = true),
            @CacheEvict(cacheNames = AppCacheProperties.CacheNames.BOOKS_BY_CATEGORY_NAME, allEntries = true)
    })
    public void delete(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("book id-{} not found", id).getMessage()));
        book.getCategory().deleteBook(book);
        bookRepository.deleteById(id);
    }


    private Category getCategory(String categoryName) {
        return categoryRepository.existsCategoriesByName(categoryName) ?
                categoryRepository.findByName(categoryName).get() :
                categoryRepository.save(Category.builder()
                        .name(categoryName)
                        .build());
    }

}
