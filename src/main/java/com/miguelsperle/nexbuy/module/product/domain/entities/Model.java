package com.miguelsperle.nexbuy.module.product.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Model {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private Model(String id, String name, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static Model newModel(String name) {
        return new Model(UUID.randomUUID().toString(), name, LocalDateTime.now());
    }

    public static Model with(String id, String name, LocalDateTime createdAt) {
        return new Model(id, name, createdAt);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
