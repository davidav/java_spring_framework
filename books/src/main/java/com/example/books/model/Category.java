package com.example.books.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")//, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (books == null){
            books = new ArrayList<>();
        }
        books.add(book);
    }

    public void deleteBook(Book book){
        books.remove(book);
    }



}
