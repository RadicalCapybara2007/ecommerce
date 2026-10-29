package com.ecommerce.controllers;

import com.ecommerce.models.ProductSummary;
import com.ecommerce.services.CompositeApiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductSummaryController {

    private final CompositeApiService compositeApiService;

    public ProductSummaryController(CompositeApiService compositeApiService) {
        this.compositeApiService = compositeApiService;
    }

    @GetMapping("/product-summary/{id}")
    public ProductSummary getSummary(@PathVariable String id) {
        return compositeApiService.getProductSummary(id);
    }
}