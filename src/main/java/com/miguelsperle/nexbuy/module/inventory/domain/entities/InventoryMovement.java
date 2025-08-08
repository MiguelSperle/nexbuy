package com.miguelsperle.nexbuy.module.inventory.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;

import java.time.LocalDateTime;

public class InventoryMovement {
    private final String id;
    private final String inventoryId;
    private final String sku;
    private final Integer quantity;
    private final InventoryMovementType movementType;
    private final LocalDateTime createdAt;

    private InventoryMovement(
            String id,
            String inventoryId,
            String sku,
            Integer quantity,
            InventoryMovementType movementType,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.inventoryId = inventoryId;
        this.sku = sku;
        this.quantity = quantity;
        this.movementType = movementType;
        this.createdAt = createdAt;
    }

    public static InventoryMovement newInventoryMovement(
            String inventoryId,
            String sku,
            Integer quantity,
            InventoryMovementType movementType
    ) {
        return new InventoryMovement(
                IdentifierUtils.generateUUID(),
                inventoryId,
                sku,
                quantity,
                movementType,
                TimeUtils.now()
        );
    }

    public static InventoryMovement with(
            String id,
            String inventoryId,
            String sku,
            Integer quantity,
            InventoryMovementType movementType,
            LocalDateTime createdAt
    ) {
        return new InventoryMovement(
                id,
                inventoryId,
                sku,
                quantity,
                movementType,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getInventoryId() {
        return this.inventoryId;
    }

    public String getSku() {
        return this.sku;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public InventoryMovementType getMovementType() {
        return this.movementType;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "InventoryMovement{" +
                "id='" + this.id + '\'' +
                ", inventoryId='" + this.inventoryId + '\'' +
                ", sku='" + this.sku + '\'' +
                ", quantity=" + this.quantity +
                ", movementType=" + this.movementType +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
