package com.example.news.controller;

import com.example.news.StringTestUtils;
import com.example.news.dto.PagesRequest;
import com.example.news.dto.category.CategoryFilter;
import com.example.news.dto.category.CategoryListResponse;
import com.example.news.dto.category.CategoryResponse;
import com.example.news.dto.category.UpsertCategoryRequest;
import com.example.news.dto.mapper.CategoryMapper;
import com.example.news.dto.news.NewsWithoutContactsResponse;
import com.example.news.model.Category;
import com.example.news.model.Comment;
import com.example.news.model.News;
import com.example.news.model.User;
import com.example.news.service.CategoryService;
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

class CategoryControllerTest extends AbstractTestController {

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private CategoryMapper categoryMapper;

    @Test
    public void whenFindAll_thenReturnAllCategories() throws Exception {

        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        List<Category> categories = new ArrayList<>(List.of(category));

        NewsWithoutContactsResponse newsWithoutContactsResponse = createNewsWithoutContactsResponse(
                "Title news 1", "Text news 1 Text news 1 Text news 1", 1,1L,1L);
        CategoryResponse categoryResponse = createCategoryResponse("Name category 1", newsWithoutContactsResponse);
        CategoryListResponse categoryListResponse = new CategoryListResponse(List.of(categoryResponse));
        PagesRequest pagesRequest = createPageRequest(10, 0);

        Mockito.when(categoryService.findAll(pagesRequest)).thenReturn(categories);
        Mockito.when(categoryMapper.categoryListToCategoryListResponse(categories)).thenReturn(categoryListResponse);
        String actualResponse = mockMvc.perform(get("/api/category?pageSize=10&pageNumber=0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_categories_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).findAll(pagesRequest);
        Mockito.verify(categoryMapper, Mockito.times(1)).categoryListToCategoryListResponse(categories);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindAllByFilter_thenReturnAllCategoriesFiltered() throws Exception {

        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        List<Category> categories = new ArrayList<>(List.of(category));
        NewsWithoutContactsResponse newsWithoutContactsResponse = createNewsWithoutContactsResponse(
                "Title news 1", "Text news 1 Text news 1 Text news 1", 1,1L,1L);
        CategoryResponse categoryResponse = createCategoryResponse("Name category 1", newsWithoutContactsResponse);
        CategoryListResponse categoryListResponse = new CategoryListResponse(List.of(categoryResponse));

        CategoryFilter categoryFilter = new CategoryFilter();
        categoryFilter.setName("Name category 1");
        categoryFilter.setPageSize(10);
        categoryFilter.setPageNumber(0);

        Mockito.when(categoryService.filterBy(categoryFilter)).thenReturn(categories);
        Mockito.when(categoryMapper.categoryListToCategoryListResponse(categories)).thenReturn(categoryListResponse);
        String actualResponse = mockMvc.perform(get("/api/category/filter?pageSize=10&pageNumber=0&name=Name category 1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_categories_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).filterBy(categoryFilter);
        Mockito.verify(categoryMapper, Mockito.times(1)).categoryListToCategoryListResponse(categories);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenFindById_thenReturnCategory() throws Exception{
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        NewsWithoutContactsResponse newsWithoutContactsResponse = createNewsWithoutContactsResponse(
                "Title news 1", "Text news 1 Text news 1 Text news 1", 1,1L,1L);
        CategoryResponse categoryResponse = createCategoryResponse("Name category 1", newsWithoutContactsResponse);

        Mockito.when(categoryService.findById(1L)).thenReturn(category);
        Mockito.when(categoryMapper.categoryToResponse(category)).thenReturn(categoryResponse);
        String actualResponse = mockMvc.perform(get("/api/category/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_category_by_id_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).findById(1L);
        Mockito.verify(categoryMapper, Mockito.times(1)).categoryToResponse(category);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenCreateCategory_thenReturnNewCategory() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        NewsWithoutContactsResponse newsWithoutContactsResponse = createNewsWithoutContactsResponse(
                "Title news 1", "Text news 1 Text news 1 Text news 1", 1,1L,1L);
        CategoryResponse categoryResponse = createCategoryResponse("Name category 1", newsWithoutContactsResponse);
        UpsertCategoryRequest upsertCategoryRequest = new UpsertCategoryRequest("Name category 1");

        Mockito.when(categoryService.save(category)).thenReturn(category);
        Mockito.when(categoryMapper.requestToCategory(upsertCategoryRequest)).thenReturn(category);
        Mockito.when(categoryMapper.categoryToResponse(category)).thenReturn(categoryResponse);
        String actualResponse = mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upsertCategoryRequest)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/create_category_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).save(category);
        Mockito.verify(categoryMapper, Mockito.times(1)).categoryToResponse(category);
        Mockito.verify(categoryMapper, Mockito.times(1)).requestToCategory(upsertCategoryRequest);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenUpdateCategory_whenReturnUpdatedCategory() throws Exception {
        User user = createUser(1L, "Andre", "David", null, null);
        Category category = createCategory(1L, "Name category 1", null);
        News news = createNews(1L, "Title news 1", "Text news 1 Text news 1 Text news 1", user, category, null);
        Comment comment = createComment(1L, "This is comment 1 for news 1 from user 1", user, news);
        news.addComment(comment);
        user.addComment(comment);
        user.addNews(news);
        category.addNews(news);
        NewsWithoutContactsResponse newsWithoutContactsResponse = createNewsWithoutContactsResponse(
                "Title news 1", "Text news 1 Text news 1 Text news 1", 1,1L,1L);
        CategoryResponse categoryResponse = createCategoryResponse("Name category 1", newsWithoutContactsResponse);
        UpsertCategoryRequest upsertCategoryRequest = new UpsertCategoryRequest("Name category 1");

        Mockito.when(categoryService.update(category)).thenReturn(category);
        Mockito.when(categoryMapper.requestToCategory(1L, upsertCategoryRequest)).thenReturn(category);
        Mockito.when(categoryMapper.categoryToResponse(category)).thenReturn(categoryResponse);
        String actualResponse = mockMvc.perform(put("/api/category/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upsertCategoryRequest)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = StringTestUtils
                .readStringFromResource("response/update_category_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).update(category);
        Mockito.verify(categoryMapper, Mockito.times(1)).categoryToResponse(category);
        Mockito.verify(categoryMapper, Mockito.times(1)).requestToCategory(1L, upsertCategoryRequest);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenDeleteCategoryById_thenReturnStatusNoContent() throws Exception {

        mockMvc.perform(delete("/api/category/1"));

        Mockito.verify(categoryService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void whenFindByIdNotExistedCategory_thenReturnError() throws Exception{
        Mockito.when(categoryService.findById(100L)).thenThrow(new EntityNotFoundException("Категория с id 100 не найдена"));

        var response = mockMvc.perform(get("/api/category/100"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/category_by_id_not_found_response.json");

        Mockito.verify(categoryService, Mockito.times(1)).findById(100L);
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenCreateCategoryWithEmptyName_thenReturnError() throws Exception {

        UpsertCategoryRequest upsertCategoryRequest = new UpsertCategoryRequest(null);

        var response = mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(upsertCategoryRequest)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();

        response.setCharacterEncoding("UTF-8");

        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/empty_category_name_response.json");
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @ParameterizedTest
    @MethodSource("invalidSizeName")
    public void whenCreateCategoryWithInvalidSizeName_thenReturnError(String name) throws Exception{
        var response = mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpsertCategoryRequest(name))))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse();
        response.setCharacterEncoding("UTF-8");
        String actualResponse = response.getContentAsString();
        String expectedResponse = StringTestUtils.readStringFromResource(
                "response/category_name_size_exception_response.json");

        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    private static Stream<Arguments> invalidSizeName(){
        return Stream.of(
                Arguments.of(RandomString.make(2)),
                Arguments.of(RandomString.make(31))
        );
    }

}