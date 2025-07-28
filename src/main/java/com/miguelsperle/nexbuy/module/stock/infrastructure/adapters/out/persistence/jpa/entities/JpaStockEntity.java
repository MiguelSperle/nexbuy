package com.miguelsperle.nexbuy.module.stock.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.stock.domain.entities.Stock;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@AllArgsConstructor
@NoArgsConstructor
public class JpaStockEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "product_id", nullable = false, unique = true, length = 36)
    private String productId;

    @Column(nullable = false, unique = true, length = 80)
    private String sku;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaStockEntity from(Stock stock) {
        return new JpaStockEntity(
                stock.getId(),
                stock.getProductId(),
                stock.getSku(),
                stock.getQuantity(),
                stock.getCreatedAt()
        );
    }

    public Stock toEntity() {
        return Stock.with(
                this.id,
                this.productId,
                this.sku,
                this.quantity,
                this.createdAt
        );
    }
}
