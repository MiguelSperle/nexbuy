package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Category {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private Category(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Category newCategory(String name) {
        return new Category(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static Category with(String id, String name, LocalDateTime createdAt) {
        return new Category(id, name, createdAt);
    }
}
