package com.example.books.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BookResponse implements Serializable {
    private String title;
    private String author;
    private String categoryName;
}
