package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Category {
    private final String id;
    private final String name;
    private final String description;
    private final String slug;
    private final Category parentCategory;
    private final LocalDateTime createdAt;

    private Category(String id, String name, String description, String slug, Category parentCategory, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.parentCategory = parentCategory;
        this.createdAt = createdAt;
    }

    public static Category newCategory(String name, String description, String slug, Category parentCategory) {
        return new Category(UUID.randomUUID().toString(), name, description, slug, parentCategory, LocalDateTime.now());
    }

    public static Category with(String id, String name, String description, String slug, Category parentCategory, LocalDateTime createdAt) {
        return new Category(id, name, description, slug, parentCategory, createdAt);
    }
}
