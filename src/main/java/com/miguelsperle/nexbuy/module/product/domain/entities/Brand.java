package com.miguelsperle.nexbuy.module.product.domain.entities;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
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
}
