package com.example.books.dto;

import lombok.Data;

@Data
public class UpsertBookRequest {

    private String title;
    private String author;
    private String category;
}
