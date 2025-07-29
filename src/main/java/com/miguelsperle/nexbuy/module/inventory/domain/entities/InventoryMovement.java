package com.miguelsperle.nexbuy.module.inventory.domain.entities;

import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;

import java.time.LocalDateTime;
import java.util.UUID;

public class InventoryMovement {
    private final String id;
    private final String inventoryId;
    private final Integer quantity;
    private final InventoryMovementType inventoryMovementType;
    private final LocalDateTime createdAt;

    private InventoryMovement(
            String id,
            String inventoryId,
            Integer quantity,
            InventoryMovementType inventoryMovementType,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.inventoryId = inventoryId;
        this.quantity = quantity;
        this.inventoryMovementType = inventoryMovementType;
        this.createdAt = createdAt;
    }

    public static InventoryMovement newInventoryMovement(
            String inventoryId,
            Integer quantity,
            InventoryMovementType inventoryMovementType
    ) {
        return new InventoryMovement(
                UUID.randomUUID().toString(),
                inventoryId,
                quantity,
                inventoryMovementType,
                LocalDateTime.now()
        );
    }

    public static InventoryMovement with(
            String id,
            String inventoryId,
            Integer quantity,
            InventoryMovementType inventoryMovementType,
            LocalDateTime createdAt
    ) {
        return new InventoryMovement(
                id,
                inventoryId,
                quantity,
                inventoryMovementType,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getInventoryId() {
        return this.inventoryId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public InventoryMovementType getInventoryMovementType() {
        return this.inventoryMovementType;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "InventoryMovement{" +
                "id='" + this.id + '\'' +
                ", inventoryId='" + this.inventoryId + '\'' +
                ", quantity=" + this.quantity +
                ", inventoryMovementType=" + this.inventoryMovementType +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
