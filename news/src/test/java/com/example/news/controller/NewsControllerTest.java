package com.example.news.controller;

import com.example.news.StringTestUtils;
import com.example.news.dto.PagesRequest;
import com.example.news.dto.mapper.NewsMapper;
import com.example.news.dto.news.NewsWithoutContactsResponse;
import com.example.news.model.Category;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.NewsService;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NewsControllerTest extends AbstractTestController{

    @MockBean
    private NewsService newsService;
    @MockBean
    private NewsMapper newsMapper;

    @Test
    public void whenFindAll_thenReturnAllNewses() throws Exception {

        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        List<News> newses = new ArrayList<>(List.of(news));
        NewsWithoutContactsResponse newsWithoutContactsResponse = createNewsWithoutContactsResponse("Title news 1", "Text news 1 Text news 1 Text news 1", 1, 1L, 1L);
        PagesRequest pagesRequest = createPageRequest(10, 0);

        Mockito.when(newsService.findAll(pagesRequest)).thenReturn(newses);
        Mockito.when(newsMapper.newsWithoutContactsToResponse(news)).thenReturn(newsWithoutContactsResponse);
        String actualResponse = mockMvc.perform(get("/api/news?pageSize=10&pageNumber=0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_newses_response.json");

        Mockito.verify(newsService, Mockito.times(1)).findAll(pagesRequest);
        Mockito.verify(newsMapper, Mockito.times(1)).newsWithoutContactsToResponse(news);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);


    }




}