package com.miguelsperle.nexbuy.module.order.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.order.domain.entities.Order;
import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class JpaOrderEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private OrderStatus orderStatus;

    @Column(unique = true, nullable = false, length = 12)
    private String code;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaOrderEntity from(Order order) {
        return new JpaOrderEntity(
                order.getId(),
                order.getUserId(),
                order.getOrderStatus(),
                order.getCode(),
                order.getTotalAmount(),
                order.getCreatedAt()
        );
    }

    public Order toEntity() {
        return Order.with(
                this.id,
                this.userId,
                this.orderStatus,
                this.code,
                this.totalAmount,
                this.createdAt
        );
    }
}
