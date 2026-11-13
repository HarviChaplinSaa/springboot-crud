package com.felipe.curso.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.felipe.curso.springboot.app.springboot_crud.entities.Product;
import com.felipe.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:4200"}, originPatterns = "*")
public class ProductController {
    
    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    /**
     * This method is used to validate the request body and return a bad request response if there are any validation errors.
     * @param result
     * @return ResponseEntity with a map of field errors if there are any validation errors, or null if there are no validation errors.
     */
    private ResponseEntity<?> validation(BindingResult result){
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "The field "+err.getField()+" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @GetMapping
    public List<Product> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> view(
        @PathVariable Long id
    ){
        return service.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(
        @Valid @RequestBody Product product,
        BindingResult result
    ){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @Valid @RequestBody Product product,
        BindingResult result,
        @PathVariable Long id
    ) {
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return service.update(id, product)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(
        @PathVariable Long id
    ){
        return service.delete(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }


}
