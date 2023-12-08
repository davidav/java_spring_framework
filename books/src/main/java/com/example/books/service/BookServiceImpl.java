package com.example.books.service;

import com.example.books.model.Book;
import com.example.books.repo.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    private final CategoryService categoryService;


    @Override
    public Book findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title,author)
                .orElseThrow(() -> new EntityNotFoundException(
                MessageFormatter.format("book title - {} author {} not found", title, author).getMessage()));
    }
}
