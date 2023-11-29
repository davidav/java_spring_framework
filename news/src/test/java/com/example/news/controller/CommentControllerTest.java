package com.example.news.controller;

import com.example.news.StringTestUtils;
import com.example.news.dto.PagesRequest;
import com.example.news.dto.comment.CommentListResponse;
import com.example.news.dto.comment.CommentResponse;
import com.example.news.dto.comment.UpsertCommentRequest;
import com.example.news.dto.mapper.CommentMapper;
import com.example.news.model.Category;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.CommentService;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest extends AbstractTestController{

    @MockBean
    private CommentService commentService;
    @MockBean
    private CommentMapper commentMapper;

    @Test
    public void whenFindAll_thenReturnAllComments() throws Exception {

        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        List<Comment> comments = new ArrayList<>(List.of(comment));
        CommentResponse commentResponse = createCommentResponse("This is comment 1 for news 1 from user 1",1L,1L);
        CommentListResponse commentListResponse = new CommentListResponse();
        commentListResponse.setComments(new ArrayList<>(List.of(commentResponse)));
        PagesRequest pagesRequest = createPageRequest(10, 0);

        Mockito.when(commentService.findAll(pagesRequest)).thenReturn(comments);
        Mockito.when(commentMapper.commentListToCommentListResponse(comments)).thenReturn(commentListResponse);
        String actualResponse = mockMvc.perform(get("/api/comment?pageSize=10&pageNumber=0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_comment_response.json");

        Mockito.verify(commentService, Mockito.times(1)).findAll(pagesRequest);
        Mockito.verify(commentMapper, Mockito.times(1)).commentListToCommentListResponse(comments);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateComment_thenReturnNewComment() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        CommentResponse commentResponse = createCommentResponse("This is comment 1 for news 1 from user 1",1L,1L);
        UpsertCommentRequest upsertCommentRequest = createUpsertCommentRequest("This is comment 1 for news 1 from user 1",1L,1L );

        Mockito.when(commentService.save(comment)).thenReturn(comment);
        Mockito.when(commentMapper.requestToComment(upsertCommentRequest)).thenReturn(comment);
        Mockito.when(commentMapper.commentToResponse(comment)).thenReturn(commentResponse);
        String actualResponse = mockMvc.perform(post("/api/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upsertCommentRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/create_comment_response.json");

        Mockito.verify(commentService, Mockito.times(1)).save(comment);
        Mockito.verify(commentMapper, Mockito.times(1)).commentToResponse(comment);
        Mockito.verify(commentMapper, Mockito.times(1)).requestToComment(upsertCommentRequest);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);


    }

    @Test
    public void whenUpdateComment_whenReturnUpdatedComment() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        CommentResponse commentResponse = createCommentResponse("This is comment 1 for news 1 from user 1",1L,1L);
        UpsertCommentRequest upsertCommentRequest = createUpsertCommentRequest("This is comment 1 for news 1 from user 1",1L,1L );

        Mockito.when(commentService.update(comment)).thenReturn(comment);
        Mockito.when(commentMapper.requestToComment(1L, upsertCommentRequest)).thenReturn(comment);
        Mockito.when(commentMapper.commentToResponse(comment)).thenReturn(commentResponse);
        String actualResponse = mockMvc.perform(put("/api/comment/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upsertCommentRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/update_comment_response.json");

        Mockito.verify(commentService, Mockito.times(1)).update(comment);
        Mockito.verify(commentMapper, Mockito.times(1)).commentToResponse(comment);
        Mockito.verify(commentMapper, Mockito.times(1)).requestToComment(1L, upsertCommentRequest);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenDeleteCommentById_thenReturnStatusNoContent() throws Exception {

        mockMvc.perform(delete("/api/comment/1?userId=1"));

        Mockito.verify(commentService, Mockito.times(1)).deleteById(1L,1L);
    }

}