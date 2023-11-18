package com.example.news.validation;

import com.example.news.dto.user.UserFilter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserFilterValidValidator implements ConstraintValidator<UserFilterValid, UserFilter> {

    @Override
    public boolean isValid(UserFilter value, ConstraintValidatorContext context) {
//        todo
        return true;
    }
}
