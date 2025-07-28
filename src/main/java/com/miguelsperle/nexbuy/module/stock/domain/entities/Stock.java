package com.miguelsperle.nexbuy.module.stock.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Stock {
    private final String id;
    private final String productId;
    private final String sku;
    private final Integer quantity;
    private final LocalDateTime createdAt;

    private Stock(
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

    public static Stock newStock(
            String productId,
            String sku,
            Integer quantity
    ) {
        return new Stock(
                UUID.randomUUID().toString(),
                productId,
                sku,
                quantity,
                LocalDateTime.now()
        );
    }

    public static Stock with(
            String id,
            String productId,
            String sku,
            Integer quantity,
            LocalDateTime createdAt
    ) {
        return new Stock(
                id,
                productId,
                sku,
                quantity,
                createdAt
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
        return "Stock{" +
                "id='" + this.id + '\'' +
                ", productId='" + this.productId + '\'' +
                ", sku='" + this.sku + '\'' +
                ", quantity=" + this.quantity +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
