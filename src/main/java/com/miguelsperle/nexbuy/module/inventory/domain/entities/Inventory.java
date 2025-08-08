package com.miguelsperle.nexbuy.module.inventory.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

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
                IdentifierUtils.generateUUID(),
                productId,
                sku,
                0,
                TimeUtils.now()
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

    public Inventory withSku(String sku) {
        return new Inventory(
                this.id,
                this.productId,
                sku,
                this.quantity,
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
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", sku='" + sku + '\'' +
                ", quantity=" + quantity +
                ", createdAt=" + createdAt +
                '}';
    }
}
