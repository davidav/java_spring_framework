package com.example.books.service;


import com.example.books.dto.BookListResponse;
import com.example.books.dto.BookResponse;
import com.example.books.dto.UpsertBookRequest;
import com.example.books.model.Book;

import java.util.List;

public interface BookService {
    BookResponse findByTitleAndAuthor(String title, String author);

    BookListResponse findAllByCategoryName(String categoryName);

    BookResponse create(UpsertBookRequest request);

    BookResponse update(Long id, UpsertBookRequest request);

    void delete(Long id);

}

