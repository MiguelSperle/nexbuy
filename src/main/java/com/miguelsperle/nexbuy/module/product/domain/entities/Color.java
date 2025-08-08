package com.miguelsperle.nexbuy.module.product.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

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
        return new Color(IdentifierUtils.generateUUID(), name, TimeUtils.now());
    }

    public static Color with(String id, String name, LocalDateTime createdAt) {
        return new Color(id, name, createdAt);
    }

    public Color withName(String name) {
        return new Color(this.id, name, this.createdAt);
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
