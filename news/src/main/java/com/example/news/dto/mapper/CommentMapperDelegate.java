package com.example.news.dto.mapper;

import com.example.news.dto.comment.CommentResponse;
import com.example.news.dto.comment.UpsertCommentRequest;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.NewsService;
import com.example.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class CommentMapperDelegate implements CommentMapper{

    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private UserDetails userDetails;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        User user = userService.findById(request.getUserId(), userDetails);
        comment.setUser(user);
        News news = newsService.findById(request.getNewsId());
        comment.setNews(news);

        return comment;
    }

    @Override
    public Comment requestToComment(Long id, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(id);

        return comment;
    }


    @Override
    public CommentResponse commentToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setComment(comment.getComment());
        response.setUserId(comment.getUser().getId());
        response.setNewsId(comment.getNews().getId());

        return response;
    }
}
