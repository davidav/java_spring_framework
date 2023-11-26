package com.example.news.controller;

import com.example.news.dto.PagesRequest;
import com.example.news.dto.category.CategoryFilter;
import com.example.news.dto.category.CategoryListResponse;
import com.example.news.dto.category.CategoryResponse;
import com.example.news.dto.category.UpsertCategoryRequest;
import com.example.news.dto.mapper.CategoryMapper;
import com.example.news.model.Category;
import com.example.news.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@Tag(name = "Category ", description = "Category API")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    @GetMapping("/filter")
    public ResponseEntity<CategoryListResponse> findAllByFilter(@Valid CategoryFilter filter) {
        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryListResponse(
                        categoryService.filterBy(filter)));
    }


    @GetMapping
    public ResponseEntity<CategoryListResponse> findAll(@Valid PagesRequest request) {

        return ResponseEntity.ok(
                categoryMapper.categoryListToCategoryListResponse(
                        categoryService.findAll(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                categoryMapper.categoryToResponse(
                        categoryService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid UpsertCategoryRequest request) {
        Category newCategory = categoryService.save(categoryMapper.requestToCategory(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryMapper.categoryToResponse(newCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertCategoryRequest request) {
        Category updateCategory = categoryService.update(categoryMapper.requestToCategory(id, request));

        return ResponseEntity.ok(categoryMapper.categoryToResponse(updateCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
