package com.example.books.mapper.v1;

import com.example.books.dto.BookResponse;
import com.example.books.model.Book;

public abstract class BookMapperV2Delegate implements BookMapperV2 {

    @Override
    public BookResponse bookToResponse(Book book) {
        return BookResponse.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .category(book.getCategory().getName())
                .build();
    }
}
