package com.example.books.mapper.v2;

import com.example.books.dto.BookListResponse;
import com.example.books.dto.BookResponse;
import com.example.books.dto.UpsertBookRequest;
import com.example.books.model.Book;
import com.example.books.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final CategoryService categoryService;

    public BookResponse bookToResponse(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .categoryName(book.getCategory().getName())
                .build();
    }

    public BookListResponse bookListToBookListResponse(List<Book> books) {
        BookListResponse response = new BookListResponse();
        response.setBooks(books.stream().map(this::bookToResponse).collect(Collectors.toList()));
        return response;
    }

    public Book requestToBook(UpsertBookRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .category(categoryService.findCategoryByName(request.getCategoryName()))
                .build();
    }
}
