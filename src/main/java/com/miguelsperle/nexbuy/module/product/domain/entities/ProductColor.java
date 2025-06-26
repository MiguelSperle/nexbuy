package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProductColor {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private ProductColor(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static ProductColor newProductColor(String name) {
        return new ProductColor(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static ProductColor with(String id, String name, LocalDateTime createdAt) {
        return new ProductColor(id, name, createdAt);
    }
}
