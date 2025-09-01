package com.miguelsperle.nexbuy.module.order.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItem {
    private final String id;
    private final String orderId;
    private final String productId;
    private final Integer quantity;
    private final BigDecimal unitPrice;
    private final LocalDateTime createdAt;

    private OrderItem(
            String id,
            String orderId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.createdAt = createdAt;
    }

    public static OrderItem newOrderItem(
            String orderId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice
    ) {
        return new OrderItem(
                IdentifierUtils.generateUUID(),
                orderId,
                productId,
                quantity,
                unitPrice,
                TimeUtils.now()
        );
    }

    public static OrderItem with(
            String id,
            String orderId,
            String productId,
            Integer quantity,
            BigDecimal unitPrice,
            LocalDateTime createdAt
    ) {
        return new OrderItem(
                id,
                orderId,
                productId,
                quantity,
                unitPrice,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getProductId() {
        return this.productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id='" + this.id + '\'' +
                ", orderId='" + this.orderId + '\'' +
                ", productId='" + this.productId + '\'' +
                ", quantity=" + this.quantity +
                ", unitPrice=" + this.unitPrice +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
