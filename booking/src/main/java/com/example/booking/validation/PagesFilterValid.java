package com.example.booking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PagesFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PagesFilterValid {
    String message() default "Fields for pagination must be specified";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
