package com.ecommerce.controllers;

import com.ecommerce.models.Inventory;
import com.ecommerce.interfaces.InventoryRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservices/inventory")
public class InventoryController {

    private final InventoryRepository repository;

    public InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable String id) {
        return repository.findById(id).orElse(null);
    }
}