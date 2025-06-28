package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Category {
    private final String id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;

    private Category(String id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static Category newCategory(String name, String description) {
        return new Category(UUID.randomUUID().toString(), name, description, LocalDateTime.now());
    }

    public static Category with(String id, String name, String description, LocalDateTime createdAt) {
        return new Category(id, name, description, createdAt);
    }
}
