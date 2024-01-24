package com.example.news.controller;

import com.example.news.StringTestUtils;
import com.example.news.dto.PagesRequest;
import com.example.news.dto.comment.CommentResponse;
import com.example.news.dto.mapper.NewsMapper;
import com.example.news.dto.news.NewsListResponse;
import com.example.news.dto.news.NewsResponse;
import com.example.news.dto.news.NewsWithoutContactsResponse;
import com.example.news.dto.news.UpsertNewsRequest;
import com.example.news.model.Category;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.NewsService;
import jakarta.persistence.EntityNotFoundException;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NewsControllerTest extends AbstractTestController {

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
        NewsWithoutContactsResponse newsWithoutContactsResponse = createNewsWithoutContactsResponse(
                "Title news 1", "Text news 1 Text news 1 Text news 1", 1, 1L, 1L);
        NewsListResponse newsListResponse = new NewsListResponse();
        newsListResponse.setNewses(new ArrayList<>(List.of(newsWithoutContactsResponse)));

        PagesRequest pagesRequest = createPageRequest(10, 0);

        Mockito.when(newsService.findAll(pagesRequest)).thenReturn(newses);
        Mockito.when(newsMapper.newsListToNewsListResponse(newses)).thenReturn(newsListResponse);
        String actualResponse = mockMvc.perform(get("/api/news?pageSize=10&pageNumber=0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_newses_response.json");

        Mockito.verify(newsService, Mockito.times(1)).findAll(pagesRequest);
        Mockito.verify(newsMapper, Mockito.times(1)).newsListToNewsListResponse(newses);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindById_thenReturnNews() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        category.addNews(news);
        user.addNews(news);
        CommentResponse commentResponse = createCommentResponse("This is comment 1 for news 1 from user 1", 1L, 1L);
        NewsResponse newsResponse = createNewsResponse("Title news 1", "Text news 1 Text news 1 Text news 1", 1L, 1L, commentResponse);

        Mockito.when(newsService.findById(1L)).thenReturn(news);
        Mockito.when(newsMapper.newsToResponse(news)).thenReturn(newsResponse);
        String actualResponse = mockMvc.perform(get("/api/news/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_news_by_id_response.json");

        Mockito.verify(newsService, Mockito.times(1)).findById(1L);
        Mockito.verify(newsMapper, Mockito.times(1)).newsToResponse(news);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateNews_thenReturnNewNews() throws Exception {
//        todo
//        User user = createUser(1L, "Andre", "David", null, null);
//        Category category = createCategory(1L, "Name category 1", null);
//        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
//        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
//        news.addComment(comment);
//        user.addComment(comment);
//        category.addNews(news);
//        user.addNews(news);
//        CommentResponse commentResponse = createCommentResponse("This is comment 1 for news 1 from user 1", 1L, 1L);
//        NewsResponse newsResponse = createNewsResponse("Title news 1", "Text news 1 Text news 1 Text news 1", 1L, 1L, commentResponse);
//        UpsertNewsRequest request = createUpsertNewsRequest("Title news 1", "Text news 1 Text news 1 Text news 1",1L, 1L);
//
//        Mockito.when(newsService.save(news)).thenReturn(news);
//        Mockito.when(newsMapper.requestToNews(request, userDetails)).thenReturn(news);
//        Mockito.when(newsMapper.newsToResponse(news)).thenReturn(newsResponse);
//        String actualResponse = mockMvc.perform(post("/api/news")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        String expectedResponse = StringTestUtils
//                .readStringFromResource("response/create_news_response.json");
//
//        Mockito.verify(newsService, Mockito.times(1)).save(news);
//        Mockito.verify(newsMapper, Mockito.times(1)).newsToResponse(news);
//        Mockito.verify(newsMapper, Mockito.times(1)).requestToNews(request, userDetails);
//        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateNews_whenReturnUpdatedNews() throws Exception {
//todo
//        User user = createUser(1L, "Andre", "David", null, null);
//        Category category = createCategory(1L, "Name category 1", null);
//        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
//        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
//        news.addComment(comment);
//        user.addComment(comment);
//        category.addNews(news);
//        user.addNews(news);
//        CommentResponse commentResponse = createCommentResponse("This is comment 1 for news 1 from user 1", 1L, 1L);
//        NewsResponse newsResponse = createNewsResponse("Title news 1", "Text news 1 Text news 1 Text news 1", 1L, 1L, commentResponse);
//        UpsertNewsRequest request = createUpsertNewsRequest("Title news 1", "Text news 1 Text news 1 Text news 1",1L, 1L);
//
//        Mockito.when(newsService.update(news)).thenReturn(news);
//        Mockito.when(newsMapper.requestToNews(1L, request)).thenReturn(news);
//        Mockito.when(newsMapper.newsToResponse(news)).thenReturn(newsResponse);
//        String actualResponse = mockMvc.perform(put("/api/news/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        String expectedResponse = StringTestUtils
//                .readStringFromResource("response/update_news_response.json");
//
//        Mockito.verify(newsService, Mockito.times(1)).update(news);
//        Mockito.verify(newsMapper, Mockito.times(1)).newsToResponse(news);
//        Mockito.verify(newsMapper, Mockito.times(1)).requestToNews(1L, request);
//        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenDeleteNewsById_thenReturnStatusNoContent() throws Exception {

        mockMvc.perform(delete("/api/news/1?userId=1"));

        Mockito.verify(newsService, Mockito.times(1)).deleteById(1L,1L);
    }

    @Test
    public void whenFindByIdNotExistedNews_thenReturnError() throws Exception{
        Mockito.when(newsService.findById(100L)).thenThrow(new EntityNotFoundException("Новость с id 100 не найдена"));

        var response = mockMvc.perform(get("/api/news/100"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/news_by_id_not_found_response.json");

        Mockito.verify(newsService, Mockito.times(1)).findById(100L);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }


}