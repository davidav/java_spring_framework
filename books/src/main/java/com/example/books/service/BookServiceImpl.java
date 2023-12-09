package com.example.books.service;

import com.example.books.dto.BookListResponse;
import com.example.books.dto.BookResponse;
import com.example.books.dto.UpsertBookRequest;
import com.example.books.mapper.v2.BookMapper;
import com.example.books.model.Book;
import com.example.books.repo.BookRepository;
import com.example.books.repo.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Override
    public BookResponse findByTitleAndAuthor(String title, String author) {
        return bookMapper.bookToResponse(bookRepository.findByTitleAndAuthor(title,author)
                .orElseThrow(() -> new EntityNotFoundException(
                MessageFormatter.format("book title - {} author {} not found", title, author).getMessage())));
    }

    @Override
    public BookListResponse findAllByCategoryName(String categoryName) {
        return bookMapper.bookListToBookListResponse(bookRepository.findAllByCategoryName(categoryName));
    }

    @Override
    public BookResponse create(UpsertBookRequest request) {

       categoryRepository.exists();



        return null;
    }

    @Override
    public BookResponse update(Long id, UpsertBookRequest request) {
        return null;
    }


    @Override
    public void delete(Long id) {

        bookRepository.deleteById(id);

    }
}
