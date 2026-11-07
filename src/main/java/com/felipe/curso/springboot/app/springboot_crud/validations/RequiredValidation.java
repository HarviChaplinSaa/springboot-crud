package com.felipe.curso.springboot.app.springboot_crud.validations;

import jakarta.validation.ConstraintValidator;

public class RequiredValidation implements ConstraintValidator<IsRequired, String> {

    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        return value != null && !value.trim().isEmpty();
    }
    
}
