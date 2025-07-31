package com.miguelsperle.nexbuy.module.inventory.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Inventory {
    private final String id;
    private final String sku;
    private final Integer quantity;
    private final LocalDateTime createdAt;

    private Inventory(
            String id,
            String sku,
            Integer quantity,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.sku = sku;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public static Inventory newInventory(
            String sku
    ) {
        return new Inventory(
                UUID.randomUUID().toString(),
                sku,
                0,
                LocalDateTime.now()
        );
    }

    public static Inventory with(
            String id,
            String sku,
            Integer quantity,
            LocalDateTime createdAt
    ) {
        return new Inventory(
                id,
                sku,
                quantity,
                createdAt
        );
    }

    public Inventory withQuantity(Integer quantity) {
        return new Inventory(
                this.id,
                this.sku,
                quantity,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
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
                ", sku='" + this.sku + '\'' +
                ", quantity=" + this.quantity +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
