package com.ecommerce.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    private String productId;
    private String stockStatus;
    private Integer availableUnits;
    private String location;
    private String estimatedDelivery;

    public Inventory() {}

    public Inventory(String productId, String stockStatus, Integer availableUnits, String location, String estimatedDelivery) {
        this.productId = productId;
        this.stockStatus = stockStatus;
        this.availableUnits = availableUnits;
        this.location = location;
        this.estimatedDelivery = estimatedDelivery;
    }

    public String getProductId() { return productId; }
    public String getStockStatus() { return stockStatus; }
    public Integer getAvailableUnits() { return availableUnits; }
    public String getLocation() { return location; }
    public String getEstimatedDelivery() { return estimatedDelivery; }
}