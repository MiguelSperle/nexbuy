package com.miguelsperle.nexbuy.module.payment.domain.entities;

import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;
import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private final String id;
    private final String userId;
    private final PaymentMethod paymentMethod;
    private final BigDecimal totalAmount;
    private final String orderId;
    private final PaymentStatus paymentStatus;
    private final LocalDateTime createdAt;

    private Payment(
            String id,
            String userId,
            PaymentMethod paymentMethod,
            BigDecimal totalAmount,
            String orderId,
            PaymentStatus paymentStatus,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public static Payment newPayment(
            String userId,
            BigDecimal totalAmount,
            String orderId
    ) {
        return new Payment(
                IdentifierUtils.generateUUID(),
                userId,
                PaymentMethod.CARD,
                totalAmount,
                orderId,
                PaymentStatus.PENDING,
                TimeUtils.now()
        );
    }

    public static Payment with(
            String id,
            String userId,
            PaymentMethod paymentMethod,
            BigDecimal totalAmount,
            String orderId,
            PaymentStatus paymentStatus,
            LocalDateTime createdAt
    ) {
        return new Payment(
                id,
                userId,
                paymentMethod,
                totalAmount,
                orderId,
                paymentStatus,
                createdAt
        );
    }

    public Payment withPaymentStatus(PaymentStatus paymentStatus) {
        return new Payment(
                this.id,
                this.userId,
                this.paymentMethod,
                this.totalAmount,
                this.orderId,
                paymentStatus,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", paymentMethod=" + this.paymentMethod +
                ", totalAmount=" + this.totalAmount +
                ", orderId='" + this.orderId + '\'' +
                ", paymentStatus=" + this.paymentStatus +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
