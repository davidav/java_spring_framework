package com.example.news.dto.mapper;

import com.example.news.dto.news.NewsResponse;
import com.example.news.dto.news.UpsertNewsRequest;
import com.example.news.model.Category;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.CategoryService;
import com.example.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setText(request.getText());
        news.setTitle(request.getTitle());
        User user = userService.findById(request.getUserId());
        news.setUser(user);
        Category category = categoryService.findById(request.getCategoryId());
        news.setCategory(category);

        user.addNews(news);
        category.addNews(news);

        return news;
    }

    @Override
    public News requestToNews(Long id, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(id);

        return news;
    }

    @Override
    public NewsResponse newsToResponse(News news) {
        NewsResponse newsResponse = new NewsResponse();
        newsResponse.setCountComments(news.getComments().size());
        newsResponse.setTitle(news.getTitle());
        newsResponse.setText(news.getText());
        return newsResponse;
    }
}
