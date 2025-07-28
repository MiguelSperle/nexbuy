package com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.stock.domain.entities.StockMovement;
import com.miguelsperle.nexbuy.module.stock.domain.enums.StockMovementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks_movements")
@AllArgsConstructor
@NoArgsConstructor
public class JpaStockMovementEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "stock_id", nullable = false, length = 36)
    private String stockId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "movement_type", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private StockMovementType stockMovementType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaStockMovementEntity from(StockMovement stockMovement) {
        return new JpaStockMovementEntity(
                stockMovement.getId(),
                stockMovement.getStockId(),
                stockMovement.getQuantity(),
                stockMovement.getStockMovementType(),
                stockMovement.getCreatedAt()
        );
    }

    public StockMovement toEntity() {
        return StockMovement.with(
                this.id,
                this.stockId,
                this.quantity,
                this.stockMovementType,
                this.createdAt
        );
    }
}
