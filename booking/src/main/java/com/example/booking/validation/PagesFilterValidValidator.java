package com.example.booking.validation;


import com.example.booking.dto.PagesRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.ObjectUtils;

public class PagesFilterValidValidator implements ConstraintValidator<PagesFilterValid, PagesRequest> {

    @Override
    public boolean isValid(PagesRequest value, ConstraintValidatorContext context) {
        return !ObjectUtils.anyNull(value.getPageNumber(), value.getPageSize());
    }
}
