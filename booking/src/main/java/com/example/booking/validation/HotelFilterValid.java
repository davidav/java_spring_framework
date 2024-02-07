package com.example.booking.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HotelFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HotelFilterValid {

    String message() default "Pagination fields must be specified";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
