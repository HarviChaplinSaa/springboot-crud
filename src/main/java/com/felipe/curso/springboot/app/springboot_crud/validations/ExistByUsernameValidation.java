package com.felipe.curso.springboot.app.springboot_crud.validations;

import org.springframework.stereotype.Component;

import com.felipe.curso.springboot.app.springboot_crud.services.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String> {

    private UserService service;

    public ExistByUsernameValidation(UserService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return !service.existsByUsername(value);
    }

}
