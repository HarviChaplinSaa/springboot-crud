package com.felipe.curso.springboot.app.springboot_crud.validations;

import org.springframework.stereotype.Component;

import com.felipe.curso.springboot.app.springboot_crud.repositories.ProductRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistDbValidation implements ConstraintValidator<ExistDb, String> {

    private ProductRepository repository;

    public ExistDbValidation(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !repository.existsBySku(value);
    }
    
}
