package com.miguelsperle.nexbuy.module.product.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Brand {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private Brand(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Brand newBrand(String name) {
        return new Brand(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static Brand with(String id, String name, LocalDateTime createdAt) {
        return new Brand(id, name, createdAt);
    }

    public Brand withName(String name) {
        return new Brand(this.id, name, this.createdAt);
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
        return "Brand{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
