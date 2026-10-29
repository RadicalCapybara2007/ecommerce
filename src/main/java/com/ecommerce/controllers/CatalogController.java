package com.ecommerce.controllers;

import com.ecommerce.models.Product;
import com.ecommerce.interfaces.ProductRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservices/products")
public class CatalogController {

    private final ProductRepository repository;

    public CatalogController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return repository.findById(id).orElse(null);
    }
}