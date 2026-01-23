package com.miguelsperle.nexbuy.module.order.domain.entities;

import com.miguelsperle.nexbuy.module.order.domain.enums.OrderStatus;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private final String id;
    private final String userId;
    private final OrderStatus orderStatus;
    private final String orderNumber;
    private final BigDecimal totalAmount;
    private final LocalDateTime createdAt;

    private Order(
            String id,
            String userId,
            OrderStatus orderStatus,
            String orderNumber,
            BigDecimal totalAmount,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public static Order newOrder(
            String userId,
            String orderNumber,
            BigDecimal totalAmount
    ) {
        return new Order(
                IdentifierUtils.generateUUID(),
                userId,
                OrderStatus.WAITING_PAYMENT,
                orderNumber,
                totalAmount,
                TimeUtils.now()
        );
    }

    public static Order with(
            String id,
            String userId,
            OrderStatus orderStatus,
            String orderNumber,
            BigDecimal totalAmount,
            LocalDateTime createdAt
    ) {
        return new Order(
                id,
                userId,
                orderStatus,
                orderNumber,
                totalAmount,
                createdAt
        );
    }

    public Order withOrderStatus(OrderStatus orderStatus) {
        return new Order(
                this.id,
                this.userId,
                orderStatus,
                this.orderNumber,
                this.totalAmount,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public OrderStatus getOrderStatus() {
        return this.orderStatus;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", orderStatus=" + this.orderStatus +
                ", orderNumber='" + this.orderNumber + '\'' +
                ", totalAmount=" + this.totalAmount +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
