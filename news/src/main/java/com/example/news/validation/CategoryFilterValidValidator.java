package com.example.news.validation;


import com.example.news.dto.category.CategoryFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryFilterValidValidator implements ConstraintValidator<CategoryFilterValid, CategoryFilter> {
    @Override
    public boolean isValid(CategoryFilter value, ConstraintValidatorContext context) {
//        todo
        return true;
    }
}
