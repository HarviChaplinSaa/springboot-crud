package com.felipe.curso.springboot.app.springboot_crud.validations;

import org.springframework.stereotype.Component;

import com.felipe.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistDbValidation implements ConstraintValidator<ExistDb, String> {

    private ProductService service;

    public ExistDbValidation(ProductService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !service.existsBySku(value);
    }
    
}
