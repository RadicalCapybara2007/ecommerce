package com.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private Double priceUsd;

    public Product() {}

    public Product(String id, String name, String description, String category, Double priceUsd) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.priceUsd = priceUsd;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public Double getPriceUsd() { return priceUsd; }
}