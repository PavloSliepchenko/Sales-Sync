package com.example.salessync.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstCapitalLetterValidator.class)
public @interface ValidFirstCapitalLetter {
    String message() default "The first letter must be capital";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
