package com.example.news.dto.mapper;

import com.example.news.dto.news.NewsResponse;
import com.example.news.dto.news.NewsWithoutContactsResponse;
import com.example.news.dto.news.UpsertNewsRequest;
import com.example.news.model.Category;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.CategoryService;
import com.example.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Collectors;


public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public News requestToNews(UpsertNewsRequest request, UserDetails userDetails) {
        News news = new News();
        User existUser = userService.findByLogin(userDetails.getUsername());
        news.setText(request.getText());
        news.setTitle(request.getTitle());
        news.setUser(existUser);
        Category category = categoryService.findById(request.getCategoryId());
        news.setCategory(category);

        return news;
    }

    @Override
    public News requestToNews(Long id, UpsertNewsRequest request, UserDetails userDetails) {
        News news = requestToNews(request, userDetails);
        news.setId(id);

        return news;
    }

    @Override
    public NewsResponse newsToResponse(News news) {
        NewsResponse response = new NewsResponse();
        response.setTitle(news.getTitle());
        response.setText(news.getText());
        response.setUserId(news.getUser().getId());
        response.setCategoryId(news.getCategory().getId());
        response.setComments(news.getComments().stream()
                .map(commentMapper::commentToResponse)
                .collect(Collectors.toList()));

        return response;
    }

    @Override
    public NewsWithoutContactsResponse newsWithoutContactsToResponse(News news) {
        NewsWithoutContactsResponse response = new NewsWithoutContactsResponse();
        response.setTitle(news.getTitle());
        response.setText(news.getText());
        response.setUserId(news.getUser().getId());
        response.setCategoryId(news.getCategory().getId());
        response.setCountComments(news.getComments().size());

        return response;
    }

}
