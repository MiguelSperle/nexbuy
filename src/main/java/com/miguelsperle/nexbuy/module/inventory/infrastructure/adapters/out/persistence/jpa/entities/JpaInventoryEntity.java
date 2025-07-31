package com.miguelsperle.nexbuy.module.inventory.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.inventory.domain.entities.Inventory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
@AllArgsConstructor
@NoArgsConstructor
public class JpaInventoryEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false, unique = true, length = 80)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaInventoryEntity from(Inventory inventory) {
        return new JpaInventoryEntity(
                inventory.getId(),
                inventory.getSku(),
                inventory.getQuantity(),
                inventory.getCreatedAt()
        );
    }

    public Inventory toEntity() {
        return Inventory.with(
                this.id,
                this.sku,
                this.quantity,
                this.createdAt
        );
    }
}
