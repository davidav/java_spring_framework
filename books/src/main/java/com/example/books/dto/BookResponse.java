package com.example.books.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private String title;
    private String author;
    private String categoryName;
}
