package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
public class JpaPaymentEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "session_id", length = 70)
    private String sessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "order_id", nullable = false, unique = true, length = 36)
    private String orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaPaymentEntity from(Payment payment) {
        return new JpaPaymentEntity(
                payment.getId(),
                payment.getUserId(),
                payment.getSessionId(),
                payment.getPaymentMethod(),
                payment.getTotalAmount(),
                payment.getOrderId(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()
        );
    }

    public Payment toEntity() {
        return Payment.with(
                this.id,
                this.userId,
                this.sessionId,
                this.paymentMethod,
                this.totalAmount,
                this.orderId,
                this.paymentStatus,
                this.createdAt
        );
    }
}
