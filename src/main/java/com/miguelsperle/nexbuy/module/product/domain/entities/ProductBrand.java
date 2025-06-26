package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductBrand {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private ProductBrand(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ProductBrand newProductBrand(String name) {
        return new ProductBrand(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static ProductBrand with(String id, String name, LocalDateTime createdAt) {
        return new ProductBrand(id, name, createdAt);
    }
}
