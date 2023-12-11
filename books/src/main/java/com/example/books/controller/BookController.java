package com.example.books.controller;


import com.example.books.dto.BookListResponse;
import com.example.books.dto.BookResponse;
import com.example.books.dto.UpsertBookRequest;
import com.example.books.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping("/{title}/{author}")
    public ResponseEntity<BookResponse> findBookByTitleAndAuthor(@PathVariable("title") String title,
                                                                 @PathVariable("author") String author) {
        return ResponseEntity.ok(bookService.findByTitleAndAuthor(title, author));
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<BookListResponse> findBooksByCategoryName(@PathVariable("categoryName") String categoryName){
        return ResponseEntity.ok(bookService.findAllByCategoryName(categoryName));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody UpsertBookRequest request){
        String title = request.getTitle();
        String author = request.getAuthor();
        String categoryName = request.getCategoryName();

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(title, author, categoryName));

    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable("id") Long id,
                                               @RequestBody UpsertBookRequest request){
        String title = request.getTitle();
        String author = request.getAuthor();
        String categoryName = request.getCategoryName();

        return ResponseEntity.ok(bookService.update(id, title, author, categoryName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
