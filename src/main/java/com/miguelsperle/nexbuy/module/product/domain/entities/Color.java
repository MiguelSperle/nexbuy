package com.miguelsperle.nexbuy.module.product.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Color {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private Color(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Color newColor(String name) {
        return new Color(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static Color with(String id, String name, LocalDateTime createdAt) {
        return new Color(id, name, createdAt);
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
        return "Color{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
