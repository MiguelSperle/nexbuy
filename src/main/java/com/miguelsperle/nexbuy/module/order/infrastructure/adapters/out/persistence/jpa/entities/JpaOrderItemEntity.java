package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.order.domain.entities.OrderItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
public class JpaOrderItemEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;

    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaOrderItemEntity from(OrderItem orderItem) {
        return new JpaOrderItemEntity(
                orderItem.getId(),
                orderItem.getOrderId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                LocalDateTime.now()
        );
    }

    public OrderItem toEntity() {
        return OrderItem.with(
                this.id,
                this.orderId,
                this.productId,
                this.quantity,
                this.unitPrice,
                this.createdAt
        );
    }
}
