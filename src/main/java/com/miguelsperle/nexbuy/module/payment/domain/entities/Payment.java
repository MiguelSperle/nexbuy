package com.miguelsperle.nexbuy.module.payment.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private final String id;
    private final String userId;
    private final String paymentMethodId;
    private final BigDecimal totalAmount;
    private final LocalDateTime paidAt;
    private final LocalDateTime createdAt;

    private Payment(
            String id,
            String userId,
            String paymentMethodId,
            BigDecimal totalAmount,
            LocalDateTime paidAt,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.paymentMethodId = paymentMethodId;
        this.totalAmount = totalAmount;
        this.paidAt = paidAt;
        this.createdAt = createdAt;
    }

    public static Payment newPayment(
            String userId,
            String paymentMethodId,
            BigDecimal totalAmount
    ) {
        return new Payment(
                IdentifierUtils.generateUUID(),
                userId,
                paymentMethodId,
                totalAmount,
                null,
                TimeUtils.now()
        );
    }

    public static Payment with(
            String id,
            String userId,
            String paymentMethodId,
            BigDecimal totalAmount,
            LocalDateTime paidAt,
            LocalDateTime createdAt
    ) {
        return new Payment(
                id,
                userId,
                paymentMethodId,
                totalAmount,
                paidAt,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPaymentMethodId() {
        return this.paymentMethodId;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public LocalDateTime getPaidAt() {
        return this.paidAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", paymentMethodId='" + this.paymentMethodId + '\'' +
                ", totalAmount=" + this.totalAmount +
                ", paidAt=" + this.paidAt +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
