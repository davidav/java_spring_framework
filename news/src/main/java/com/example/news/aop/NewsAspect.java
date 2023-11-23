package com.example.news.aop;

import com.example.news.model.News;
import com.example.news.repository.NewsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class NewsAspect {
    private final NewsRepository newsRepository;

    @Before("@annotation(com.example.news.aop.NewsEditAble)")
    public void editBefore(JoinPoint joinPoint) throws AuthenticationException {
        Object[] args = joinPoint.getArgs();
        News news = (News)args[0];
        News existNews = newsRepository.findById(news.getId()).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormatter.format("Новость с id {} не найдена", news.getId()).getMessage()));
        if (news.getUser() != existNews.getUser()){
            throw new AuthenticationException("Редактировать новость может только автор");
        }

    }

    @Before("@annotation(com.example.news.aop.NewsDeleteAble)")
    public void deleteBefore(JoinPoint joinPoint) throws AuthenticationException {
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        News existNews = newsRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        MessageFormatter.format("Новость с id {} не найдена", id).getMessage()));
        if (!id.equals(existNews.getUser().getId())) {
            throw new AuthenticationException("Удалить новость может только автор");
        }

    }
}
