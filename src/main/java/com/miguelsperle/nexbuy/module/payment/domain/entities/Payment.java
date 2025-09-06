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
    private final String sessionId;
    private final PaymentMethod paymentMethod;
    private final BigDecimal totalAmount;
    private final String orderId;
    private final PaymentStatus paymentStatus;
    private final LocalDateTime createdAt;

    private Payment(
            String id,
            String userId,
            String sessionId,
            PaymentMethod paymentMethod,
            BigDecimal totalAmount,
            String orderId,
            PaymentStatus paymentStatus,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.sessionId = sessionId;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public static Payment newPayment(
            String userId,
            PaymentMethod paymentMethod,
            BigDecimal totalAmount,
            String orderId
    ) {
        return new Payment(
                IdentifierUtils.generateUUID(),
                userId,
                null,
                paymentMethod,
                totalAmount,
                orderId,
                PaymentStatus.PENDING,
                TimeUtils.now()
        );
    }

    public static Payment with(
            String id,
            String userId,
            String sessionId,
            PaymentMethod paymentMethod,
            BigDecimal totalAmount,
            String orderId,
            PaymentStatus paymentStatus,
            LocalDateTime createdAt
    ) {
        return new Payment(
                id,
                userId,
                sessionId,
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
                this.sessionId,
                this.paymentMethod,
                this.totalAmount,
                this.orderId,
                paymentStatus,
                this.createdAt
        );
    }

    public Payment withSessionId(String sessionId) {
        return new Payment(
                this.id,
                this.userId,
                sessionId,
                this.paymentMethod,
                this.totalAmount,
                this.orderId,
                this.paymentStatus,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getSessionId() {
        return this.sessionId;
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
                ", sessionId='" + this.sessionId + '\'' +
                ", paymentMethod=" + this.paymentMethod +
                ", totalAmount=" + this.totalAmount +
                ", orderId='" + this.orderId + '\'' +
                ", paymentStatus=" + this.paymentStatus +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
