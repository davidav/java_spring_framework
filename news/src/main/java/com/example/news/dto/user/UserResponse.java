package com.example.news.dto.user;

import com.example.news.model.Comment;
import com.example.news.model.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstName;

    private String secondName;

    private List<News> newses = new ArrayList<>();

//    private List<Comment> comments = new ArrayList<>();

}
