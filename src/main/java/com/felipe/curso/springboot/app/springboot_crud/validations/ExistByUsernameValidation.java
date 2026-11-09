package com.felipe.curso.springboot.app.springboot_crud.validations;

import org.springframework.stereotype.Component;

import com.felipe.curso.springboot.app.springboot_crud.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String> {

    private UserRepository repository;

    public ExistByUsernameValidation(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !repository.existsByUsername(value);
    }

}
