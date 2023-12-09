package com.example.books.repo;

import com.example.books.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleAndAuthor(String title, String author);

    @Query("select * from books b where b.category.name =: categoryName")
    List<Book> findAllByCategoryName(String categoryName);

}

