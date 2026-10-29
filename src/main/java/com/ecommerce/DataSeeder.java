package com.ecommerce;

import com.ecommerce.models.Inventory;
import com.ecommerce.models.Product;
import com.ecommerce.interfaces.InventoryRepository;
import com.ecommerce.interfaces.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepo;
    private final InventoryRepository inventoryRepo;

    public DataSeeder(ProductRepository productRepo, InventoryRepository inventoryRepo) {
        this.productRepo = productRepo;
        this.inventoryRepo = inventoryRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Insertamos los datos de prueba si no existen
        if (productRepo.findById("P-101").isEmpty()) {
            productRepo.save(new Product("P-101", "Laptop Gamer XYZ", "Laptop de alto rendimiento", "Electrónica", 1200.00));
            inventoryRepo.save(new Inventory("P-101", "IN_STOCK", 15, "Almacén Central Chihuahua", "2-3 días hábiles"));
            System.out.println("✅ Datos de prueba insertados en PostgreSQL");
        }
    }
}