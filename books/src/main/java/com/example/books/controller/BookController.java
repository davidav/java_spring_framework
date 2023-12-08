package com.example.books.controller;


import com.example.books.dto.BookMapper;
import com.example.books.dto.BookResponse;
import com.example.books.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping("/{title}/{author}")
    public ResponseEntity<BookResponse> findBookByTitleAndAuthor(@PathVariable("title") String title,
                                                                 @PathVariable("author") String author) {

        return ResponseEntity.ok(bookMapper.bookToResponse(
                bookService.findByTitleAndAuthor(title, author)));
    }






}
