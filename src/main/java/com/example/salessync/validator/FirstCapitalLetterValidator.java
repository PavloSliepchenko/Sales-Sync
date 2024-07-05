package com.example.salessync.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FirstCapitalLetterValidator
        implements ConstraintValidator<ValidFirstCapitalLetter, String> {
    @Override
    public void initialize(ValidFirstCapitalLetter constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        char firstChar = value.charAt(0);
        return Character.isUpperCase(firstChar);
    }
}
