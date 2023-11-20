package com.example.news.service;

import com.example.news.aop.Editable;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.repository.NewsRepository;
import com.example.news.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
@RequiredArgsConstructor
@Slf4j
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Новость с id {} не найдена", id).getMessage()));
    }

    @Override
    public News save(News news) {

        return newsRepository.save(news);
    }

    @Override
    @Editable
    public News update(News news) {
        News existedNews = findById(news.getId());
        AppHelperUtils.copyNonNullProperties(news, existedNews);

        return newsRepository.save(existedNews);
    }

    @Override
//    @Deletable
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
}
