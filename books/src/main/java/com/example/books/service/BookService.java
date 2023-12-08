package com.example.books.service;


import com.example.books.model.Book;

public interface BookService {
    Book findByTitleAndAuthor(String title, String author);
}

