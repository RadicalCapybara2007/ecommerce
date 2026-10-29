package com.ecommerce.services;

import com.ecommerce.models.Inventory;
import com.ecommerce.models.Product;
import com.ecommerce.models.ProductSummary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;

@Service
public class CompositeApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ExchangeRateService exchangeRateService;

    public CompositeApiService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    public ProductSummary getProductSummary(String productId) {
        CompletableFuture<Product> productFuture = CompletableFuture.supplyAsync(() -> 
            restTemplate.getForObject("http://localhost:8080/microservices/products/" + productId, Product.class)
        );

        CompletableFuture<Inventory> inventoryFuture = CompletableFuture.supplyAsync(() -> 
            restTemplate.getForObject("http://localhost:8080/microservices/inventory/" + productId, Inventory.class)
        );

        CompletableFuture<Double> exchangeRateFuture = CompletableFuture.supplyAsync(
            exchangeRateService::getMxnRate
        );

        CompletableFuture.allOf(productFuture, inventoryFuture, exchangeRateFuture).join();

        try {
            Product product = productFuture.get();
            Inventory inventory = inventoryFuture.get();
            Double mxnRate = exchangeRateFuture.get();

            double priceMxn = Math.round((product.getPriceUsd() * mxnRate) * 100.0) / 100.0;

            return new ProductSummary(
                product.getId(),
                product.getName(),
                product.getPriceUsd(),
                priceMxn,
                inventory.getStockStatus(),
                inventory.getAvailableUnits(),
                "Frankfurter External API",
                "SUCCESS"
            );

        } catch (Exception e) {
            return new ProductSummary(productId, "Error", 0.0, 0.0, "UNKNOWN", 0, "None", "ERROR");
        }
    }
}