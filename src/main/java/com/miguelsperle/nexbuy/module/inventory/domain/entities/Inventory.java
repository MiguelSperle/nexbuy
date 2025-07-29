package com.miguelsperle.nexbuy.module.inventory.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Inventory {
    private final String id;
    private final String productId;
    private final String sku;
    private final Integer quantity;
    private final LocalDateTime createdAt;

    private Inventory(
            String id,
            String productId,
            String sku,
            Integer quantity,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.productId = productId;
        this.sku = sku;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public static Inventory newInventory(
            String productId,
            String sku
    ) {
        return new Inventory(
                UUID.randomUUID().toString(),
                productId,
                sku,
                0,
                LocalDateTime.now()
        );
    }

    public static Inventory with(
            String id,
            String productId,
            String sku,
            Integer quantity,
            LocalDateTime createdAt
    ) {
        return new Inventory(
                id,
                productId,
                sku,
                quantity,
                createdAt
        );
    }

    public Inventory withQuantity(Integer quantity) {
        return new Inventory(
                this.id,
                this.productId,
                this.sku,
                quantity,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getSku() {
        return this.sku;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id='" + this.id + '\'' +
                ", productId='" + this.productId + '\'' +
                ", sku='" + this.sku + '\'' +
                ", quantity=" + this.quantity +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
