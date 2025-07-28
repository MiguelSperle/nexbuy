package com.miguelsperle.nexbuy.module.stock.domain.entities;

import com.miguelsperle.nexbuy.module.stock.domain.enums.StockMovementType;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockMovement {
    private final String id;
    private final String stockId;
    private final Integer quantity;
    private final StockMovementType stockMovementType;
    private final LocalDateTime createdAt;

    private StockMovement(
            String id,
            String stockId,
            Integer quantity,
            StockMovementType stockMovementType,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.stockId = stockId;
        this.quantity = quantity;
        this.stockMovementType = stockMovementType;
        this.createdAt = createdAt;
    }

    public static StockMovement newStockMovement(
            String stockId,
            Integer quantity,
            StockMovementType stockMovementType
    ) {
        return new StockMovement(
                UUID.randomUUID().toString(),
                stockId,
                quantity,
                stockMovementType,
                LocalDateTime.now()
        );
    }

    public static StockMovement with(
            String id,
            String stockId,
            Integer quantity,
            StockMovementType stockMovementType,
            LocalDateTime createdAt
    ) {
        return new StockMovement(
                id,
                stockId,
                quantity,
                stockMovementType,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getStockId() {
        return this.stockId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public StockMovementType getStockMovementType() {
        return this.stockMovementType;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id='" + this.id + '\'' +
                ", stockId='" + this.stockId + '\'' +
                ", quantity=" + this.quantity +
                ", stockMovementType=" + this.stockMovementType +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
