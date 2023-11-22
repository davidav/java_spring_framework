package com.example.news.dto.news;

import com.example.news.model.Comment;
import com.example.news.validation.UserFilterValid;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@UserFilterValid
public class OneNewsResponse {


    private String title;

    private String text;

    private List<Comment> comments;

    private Long userId;

    private Long categoryId;

}
