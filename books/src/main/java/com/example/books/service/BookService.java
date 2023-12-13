package com.example.books.service;


import com.example.books.dto.BookListResponse;
import com.example.books.dto.BookResponse;



public interface BookService {
    BookResponse findByTitleAndAuthor(String title, String author);

    BookListResponse findAllByCategoryName(String categoryName);

    BookResponse create(String title, String author, String categoryName);

    BookResponse update(Long id, String title, String author, String categoryName);

    void delete(Long id);


}

