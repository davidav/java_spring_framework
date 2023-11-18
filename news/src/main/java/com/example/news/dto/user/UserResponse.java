package com.example.news.dto.user;

import com.example.news.model.Comment;
import com.example.news.model.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstName;
    private String lastName;
    private List<News> news;
    private List<Comment> comments;

}
