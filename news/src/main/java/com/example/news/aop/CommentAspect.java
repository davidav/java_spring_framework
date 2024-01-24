package com.example.news.aop;

import com.example.news.model.Comment;

import com.example.news.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CommentAspect {

    private final CommentRepository commentRepository;

    @Before("@annotation(com.example.news.aop.CommentEditAvailable)")
    public void editBefore(JoinPoint joinPoint) throws AuthenticationException {
        Object[] args = joinPoint.getArgs();
        Comment comment = (Comment) args[0];
        Comment existComment = commentRepository.findById(comment.getId()).orElseThrow(
                () -> new EntityNotFoundException(MessageFormatter.format(
                        "Комментарий с id {} не найден", comment.getId()).getMessage()));
        if (!Objects.equals(comment.getUser().getId(), existComment.getUser().getId())){
            throw new AuthenticationException("Редактировать Комментарий может только автор");
        }

    }

    @Before("@annotation(com.example.news.aop.CommentDeleteAvailable)")
    public void deleteBefore(JoinPoint joinPoint) throws AuthenticationException {
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        Long userId = (Long) args[1];
        Comment existComment = commentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormatter.format("Комментарий с id {} не найден", id).getMessage()));
        if (!userId.equals(existComment.getUser().getId())) {
            throw new AuthenticationException("Удалить Комментарий может только автор");
        }
    }


}
