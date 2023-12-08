package com.example.books.dto;

import com.example.books.model.Book;

public abstract class BookMapperDelegate implements BookMapper {

    @Override
    public BookResponse bookToResponse(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory().getName())
                .build();
    }
}
