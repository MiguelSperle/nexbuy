package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductModel {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private ProductModel(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ProductModel newProductModel(String name) {
        return new ProductModel(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static ProductModel with(String id, String name, LocalDateTime createdAt) {
        return new ProductModel(id, name, createdAt);
    }
}
