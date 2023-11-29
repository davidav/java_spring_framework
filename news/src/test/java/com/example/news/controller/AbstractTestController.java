package com.example.news.controller;

import com.example.news.dto.PagesRequest;
import com.example.news.dto.category.CategoryResponse;
import com.example.news.dto.comment.CommentResponse;
import com.example.news.dto.comment.UpsertCommentRequest;
import com.example.news.dto.news.NewsResponse;
import com.example.news.dto.news.NewsWithoutContactsResponse;
import com.example.news.dto.news.UpsertNewsRequest;
import com.example.news.dto.user.UpsertUserRequest;
import com.example.news.dto.user.UserResponse;
import com.example.news.model.Category;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class AbstractTestController {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;


    protected User createUser(Long id, String firstName, String secondName, News news, Comment comment) {
        User user = User.builder()
                .id(id)
                .firstName(firstName)
                .secondName(secondName)
                .newses(new ArrayList<>())
                .comments(new ArrayList<>())
                .createAt(Instant.now())
                .build();

        if (news != null) {
            news.setUser(user);
            user.addNews(news);
        }

        if (comment != null) {
            comment.setUser(user);
            user.addComment(comment);
        }

        return user;
    }
    protected UpsertUserRequest createUpsertUserRequest(String firstName, String secondName) {
        UpsertUserRequest upsertUserRequest = new UpsertUserRequest();
        upsertUserRequest.setFirstName(firstName);
        upsertUserRequest.setSecondName(secondName);

        return upsertUserRequest;
    }
    protected UserResponse createUserResponse(String firstName, String secondName, Integer countNewses, Integer countComments) {
        return UserResponse.builder()
                .firstName(firstName)
                .secondName(secondName)
                .countNewses(countNewses)
                .countComments(countComments)
                .build();
    }



    protected Category createCategory(Long id, String name, News news) {
        Category category = Category.builder()
                .id(id)
                .name(name)
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .newses(new ArrayList<>())
                .build();

        if (news != null) {
            news.setCategory(category);
            category.addNews(news);
        }

        return category;
    }
    protected CategoryResponse createCategoryResponse(String name, NewsWithoutContactsResponse news) {
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .name(name)
                .newses(new ArrayList<>())
                .build();

        if (news != null) {
            categoryResponse.addNews(news);
        }

        return categoryResponse;
    }


    protected News createNews(Long id, String title, String text, User user, Category category, Comment comment) {

        News news = News.builder()
                .id(id)
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .title(title)
                .text(text)
                .comments(new ArrayList<>())
                .build();

        if (user != null) {
            user.addNews(news);
            news.setUser(user);
        }

        if (category != null) {
            category.addNews(news);
            news.setCategory(category);
        }

        if (comment != null) {
            comment.setNews(news);
            news.addComment(comment);
        }

        return news;
    }
    protected NewsResponse createNewsResponse(String title, String text, Long userId, Long categoryId, CommentResponse commentResponse) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setTitle(title);
        newsResponse.setText(text);
        newsResponse.setUserId(userId);
        newsResponse.setCategoryId(categoryId);
        if (commentResponse != null) {
            newsResponse.addComment(commentResponse);
        }
        return newsResponse;
    }
    protected NewsWithoutContactsResponse createNewsWithoutContactsResponse(String title, String text, Integer countComments, Long userId, Long categoryId) {
        NewsWithoutContactsResponse news = new NewsWithoutContactsResponse();
        news.setTitle(title);
        news.setText(text);
        news.setCountComments(countComments);
        news.setUserId(userId);
        news.setCategoryId(categoryId);
        return news;
    }
    protected UpsertNewsRequest createUpsertNewsRequest(String title, String text, Long userId, Long categoryId){
        UpsertNewsRequest upsertNewsRequest = new UpsertNewsRequest();
        upsertNewsRequest.setText(text);
        upsertNewsRequest.setTitle(title);
        upsertNewsRequest.setUserId(userId);
        upsertNewsRequest.setCategoryId(categoryId);

        return upsertNewsRequest;
    }



    protected Comment createComment(Long id, String comment, User user, News news) {
        Comment newComment = Comment.builder()
                .id(id)
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .comment(comment)
                .build();

        if (user != null) {
            user.addComment(newComment);
            newComment.setUser(user);
        }

        if (news != null) {
            news.addComment(newComment);
            newComment.setNews(news);
        }
        return newComment;
    }
    protected CommentResponse createCommentResponse(String comment, Long userId, Long newsId) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setComment(comment);
        commentResponse.setUserId(userId);
        commentResponse.setNewsId(newsId);
        return commentResponse;
    }
    protected UpsertCommentRequest createUpsertCommentRequest(String comment, Long userId, Long newsId){
        UpsertCommentRequest upsertCommentRequest = new UpsertCommentRequest();
        upsertCommentRequest.setComment(comment);
        upsertCommentRequest.setUserId(userId);
        upsertCommentRequest.setNewsId(newsId);

        return upsertCommentRequest;
    }


    protected PagesRequest createPageRequest(Integer pageSize, Integer pageNumber) {
        PagesRequest pagesRequest = new PagesRequest();
        pagesRequest.setPageSize(pageSize);
        pagesRequest.setPageNumber(pageNumber);

        return pagesRequest;
    }





}
