package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.inventory.domain.entities.InventoryMovement;
import com.miguelsperle.nexbuy.module.inventory.domain.enums.InventoryMovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements")
@AllArgsConstructor
@NoArgsConstructor
public class JpaInventoryMovementEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "inventory_id", nullable = false, length = 36)
    private String inventoryId;

    @Column(nullable = false, length = 80)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "movement_type", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private InventoryMovementType movementType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaInventoryMovementEntity from(InventoryMovement inventoryMovement) {
        return new JpaInventoryMovementEntity(
                inventoryMovement.getId(),
                inventoryMovement.getInventoryId(),
                inventoryMovement.getSku(),
                inventoryMovement.getQuantity(),
                inventoryMovement.getMovementType(),
                inventoryMovement.getCreatedAt()
        );
    }

    public InventoryMovement toEntity() {
        return InventoryMovement.with(
                this.id,
                this.inventoryId,
                this.sku,
                this.quantity,
                this.movementType,
                this.createdAt
        );
    }
}
