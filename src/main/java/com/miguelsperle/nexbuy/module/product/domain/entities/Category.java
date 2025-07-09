package com.miguelsperle.nexbuy.module.product.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public Category withName(String name) {
        return new Category(this.id, name, this.description, this.createdAt);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
