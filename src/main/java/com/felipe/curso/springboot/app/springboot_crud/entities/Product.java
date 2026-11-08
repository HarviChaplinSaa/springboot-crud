package com.felipe.curso.springboot.app.springboot_crud.entities;

import com.felipe.curso.springboot.app.springboot_crud.validations.ExistDb;
import com.felipe.curso.springboot.app.springboot_crud.validations.IsRequired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{NotBlank.product.name}")
    @Size(min = 3, max = 100, message = "{Size.product.name}")
    private String name;

    @NotNull(message = "{NotNull.product.price}")
    @Min(value = 500, message = "{Min.product.price}")
    private Integer price;

    //@NotBlank(message = "{NotBlank.product.description}")
    @IsRequired
    @Size(min = 3, max = 200, message = "{Size.product.description}")
    private String description;

    @ExistDb
    private String sku;

    public Product(){

    }

    public Product(Long id, String name, Integer price, String description, String sku) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sku = sku;
    }
    
}
