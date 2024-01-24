package com.example.news.controller;

import com.example.news.StringTestUtils;
import com.example.news.dto.PagesRequest;
import com.example.news.dto.mapper.UserMapper;
import com.example.news.dto.user.UpsertUserRequest;
import com.example.news.dto.user.UserFilter;
import com.example.news.dto.user.UserListResponse;
import com.example.news.dto.user.UserResponse;
import com.example.news.model.Category;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import net.bytebuddy.utility.RandomString;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractTestController {

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Test
    public void whenFindAll_thenReturnAllUsers() throws Exception {

        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        List<User> users = new ArrayList<>(List.of(user));
        UserResponse userResponse = createUserResponse("Andre", "David", 1, 1);
        UserListResponse userListResponse = new UserListResponse(List.of(userResponse));
        PagesRequest pagesRequest = createPageRequest(10, 0);

        Mockito.when(userService.findAll(pagesRequest)).thenReturn(users);
        Mockito.when(userMapper.userListToUserListResponse(users)).thenReturn(userListResponse);
        String actualResponse = mockMvc.perform(get("/api/user?pageSize=10&pageNumber=0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_users_response.json");

        Mockito.verify(userService, Mockito.times(1)).findAll(pagesRequest);
        Mockito.verify(userMapper, Mockito.times(1)).userListToUserListResponse(users);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindAllByFilter_thenReturnAllUsersFiltered() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        List<User> users = new ArrayList<>(List.of(user));
        UserResponse userResponse = createUserResponse("Andre", "David", 1, 1);
        UserListResponse userListResponse = new UserListResponse(List.of(userResponse));
        UserFilter userFilter = new UserFilter();
        userFilter.setPageSize(10);
        userFilter.setPageNumber(0);
        userFilter.setFirstName("Andre");

        Mockito.when(userService.filterBy(userFilter)).thenReturn(users);
        Mockito.when(userMapper.userListToUserListResponse(users)).thenReturn(userListResponse);
        String actualResponse = mockMvc.perform(get("/api/user/filter?pageSize=10&pageNumber=0&firstName=Andre"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_users_response.json");

        Mockito.verify(userService, Mockito.times(1)).filterBy(userFilter);
        Mockito.verify(userMapper, Mockito.times(1)).userListToUserListResponse(users);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenFindById_thenReturnUser() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        category.addNews(news);
        user.addNews(news);
        UserResponse userResponse = createUserResponse("Andre", "David", 1, 1);

        Mockito.when(userService.findById(1L, userDetails)).thenReturn(user);
        Mockito.when(userMapper.userToResponse(user)).thenReturn(userResponse);
        String actualResponse = mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_user_by_id_response.json");

        Mockito.verify(userService, Mockito.times(1)).findById(1L, userDetails);
        Mockito.verify(userMapper, Mockito.times(1)).userToResponse(user);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateUser_thenReturnNewUser() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        category.addNews(news);
        user.addNews(news);
        UserResponse userResponse = createUserResponse("Andre", "David", 1, 1);
        UpsertUserRequest request = createUpsertUserRequest("Andre", "David");

        Mockito.when(userService.save(user)).thenReturn(user);
        Mockito.when(userMapper.requestToUser(request)).thenReturn(user);
        Mockito.when(userMapper.userToResponse(user)).thenReturn(userResponse);
        String actualResponse = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/create_user_response.json");

        Mockito.verify(userService, Mockito.times(1)).save(user);
        Mockito.verify(userMapper, Mockito.times(1)).userToResponse(user);
        Mockito.verify(userMapper, Mockito.times(1)).requestToUser(request);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenUpdateUser_whenReturnUpdatedUser() throws Exception {

        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        category.addNews(news);
        user.addComment(comment);
        user.addNews(news);
        UserResponse userResponse = createUserResponse("Andre", "David", 1, 1);
        UpsertUserRequest request = createUpsertUserRequest("Andre", "David");

        Mockito.when(userService.update(user, userDetails)).thenReturn(user);
        Mockito.when(userMapper.requestToUser(1L, request)).thenReturn(user);
        Mockito.when(userMapper.userToResponse(user)).thenReturn(userResponse);
        String actualResponse = mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/update_user_response.json");

        Mockito.verify(userService, Mockito.times(1)).update(user, userDetails);
        Mockito.verify(userMapper, Mockito.times(1)).userToResponse(user);
        Mockito.verify(userMapper, Mockito.times(1)).requestToUser(1L, request);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenDeleteUserById_thenReturnStatusNoContent() throws Exception {

        mockMvc.perform(delete("/api/user/1"));

        Mockito.verify(userService, Mockito.times(1)).deleteById(1L, userDetails);
    }

    @Test
    public void whenFindByIdNotExistedUser_thenReturnError() throws Exception{
        Mockito.when(userService.findById(100L, userDetails)).thenThrow(new EntityNotFoundException("Пользователь с id 100 не найден"));

        var response = mockMvc.perform(get("/api/user/100"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/user_by_id_not_found_response.json");

        Mockito.verify(userService, Mockito.times(1)).findById(100L, userDetails);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateUserWithEmptyFirstName_thenReturnError() throws Exception {

        UpsertUserRequest request = createUpsertUserRequest(null, "David");

        var response = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/empty_user_first_name_response.json");
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateUserWithEmptySecondName_thenReturnError() throws Exception {

        UpsertUserRequest request = createUpsertUserRequest("Andre", null);

        var response = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/empty_user_second_name_response.json");
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @ParameterizedTest
    @MethodSource("invalidSizeName")
    public void whenCreateUserWithInvalidSizeFirstName_thenReturnError(String firstName) throws Exception{
        var response = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertUserRequest(firstName, "David"))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/user_first_name_size_exception_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @ParameterizedTest
    @MethodSource("invalidSizeName")
    public void whenCreateUserWithInvalidSizeSecondName_thenReturnError(String secondName) throws Exception{
        var response = mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertUserRequest("Andre", secondName))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/user_second_name_size_exception_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    private static Stream<Arguments> invalidSizeName(){
        return Stream.of(
                Arguments.of(RandomString.make(2)),
                Arguments.of(RandomString.make(31))
        );
    }

}