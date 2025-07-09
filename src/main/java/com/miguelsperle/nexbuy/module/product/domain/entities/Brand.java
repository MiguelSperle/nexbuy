package com.miguelsperle.nexbuy.module.product.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Brand {
    private final String id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;

    private Brand(String id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public static Brand newBrand(String name, String description) {
        return new Brand(UUID.randomUUID().toString(), name, description, LocalDateTime.now());
    }

    public static Brand with(String id, String name, String description, LocalDateTime createdAt) {
        return new Brand(id, name, description, createdAt);
    }

    public Brand withName(String name) {
        return new Brand(this.id, name, this.description, this.createdAt);
    }

    public Brand withDescription(String description) {
        return new Brand(this.id, this.name, description, this.createdAt);
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
        return "Brand{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
