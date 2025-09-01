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
    private final String code;
    private final BigDecimal totalAmount;
    private final String paymentMethodId;
    private final LocalDateTime createdAt;

    private Order(
            String id,
            String userId,
            OrderStatus orderStatus,
            String code,
            BigDecimal totalAmount,
            String paymentMethodId,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.code = code;
        this.totalAmount = totalAmount;
        this.paymentMethodId = paymentMethodId;
        this.createdAt = createdAt;
    }

    public static Order newOrder(
            String userId,
            String code,
            BigDecimal totalAmount,
            String paymentMethodId
    ) {
        return new Order(
                IdentifierUtils.generateUUID(),
                userId,
                OrderStatus.WAITING_PAYMENT,
                code,
                totalAmount,
                paymentMethodId,
                TimeUtils.now()
        );
    }

    public static Order with(
            String id,
            String userId,
            OrderStatus orderStatus,
            String code,
            BigDecimal totalAmount,
            String paymentMethodId,
            LocalDateTime createdAt
    ) {
        return new Order(
                id,
                userId,
                orderStatus,
                code,
                totalAmount,
                paymentMethodId,
                createdAt
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

    public String getCode() {
        return this.code;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public String getPaymentMethodId() {
        return this.paymentMethodId;
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
                ", code='" + this.code + '\'' +
                ", totalAmount=" + this.totalAmount +
                ", paymentMethodId='" + this.paymentMethodId + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
