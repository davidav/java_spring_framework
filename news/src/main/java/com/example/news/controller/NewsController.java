package com.example.news.controller;

import com.example.news.dto.mapper.NewsMapper;
import com.example.news.dto.news.NewsListResponse;
import com.example.news.dto.news.OneNewsResponse;
import com.example.news.dto.news.UpsertNewsRequest;
import com.example.news.model.News;
import com.example.news.service.NewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
@Tag(name = "News", description = "News API")
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping
    public ResponseEntity<NewsListResponse> findAll() {

        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OneNewsResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                newsMapper.oneNewsToResponse(
                        newsService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OneNewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News newNews = newsService.save(newsMapper.requestToNews(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.oneNewsToResponse(newNews));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OneNewsResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertNewsRequest request) {
        News updateNews = newsService.update(newsMapper.requestToNews(id, request));

//todo error - detached entity passed to persist

        return ResponseEntity.ok(newsMapper.oneNewsToResponse(updateNews));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
