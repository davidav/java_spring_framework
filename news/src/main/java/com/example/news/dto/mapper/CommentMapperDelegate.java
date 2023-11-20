package com.example.news.dto.mapper;

import com.example.news.dto.comment.UpsertCommentRequest;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.NewsService;
import com.example.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CommentMapperDelegate implements CommentMapper{

    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        User user = userService.findById(request.getUserId());
        comment.setUser(user);
        News news = newsService.findById(request.getNewsId());
        comment.setNews(news);

        user.addComment(comment);
        news.addComment(comment);

        return comment;
    }
}
