package com.miguelsperle.nexbuy.module.product.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

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
        return new Category(IdentifierUtils.generateUUID(), name, TimeUtils.now());
    }

    public static Category with(String id, String name, LocalDateTime createdAt) {
        return new Category(id, name, createdAt);
    }

    public Category withName(String name) {
        return new Category(this.id, name, this.createdAt);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
