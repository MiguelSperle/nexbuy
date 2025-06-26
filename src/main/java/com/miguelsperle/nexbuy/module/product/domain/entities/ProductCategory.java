package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductCategory {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private ProductCategory(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ProductCategory newProductCategory(String name) {
        return new ProductCategory(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static ProductCategory with(String id, String name, LocalDateTime createdAt) {
        return new ProductCategory(id, name, createdAt);
    }
}
