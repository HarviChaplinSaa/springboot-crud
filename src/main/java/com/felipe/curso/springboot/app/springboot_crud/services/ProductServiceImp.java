package com.felipe.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felipe.curso.springboot.app.springboot_crud.entities.Product;
import com.felipe.curso.springboot.app.springboot_crud.repositories.ProductRepository;


@Service
public class ProductServiceImp implements ProductService{

    private ProductRepository repository;

    public ProductServiceImp(ProductRepository repository){
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }
    
    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        return repository.findById(id).map(p -> {
            product.setId(id);
            return repository.save(product);
        });
    }

    @Transactional
    @Override
    public Optional<Product> delete(Product product) {
        Optional<Product> productDb = repository.findById(product.getId());
        productDb.ifPresent(repository::delete);
        return productDb;
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productDb = repository.findById(id);
        productDb.ifPresent(repository::delete);
        return productDb;
    }
    
}
