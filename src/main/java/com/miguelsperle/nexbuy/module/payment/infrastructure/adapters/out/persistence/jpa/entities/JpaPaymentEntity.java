package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "payment_method_id", nullable = false, length = 36)
    private String paymentMethodId;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaPaymentEntity from(Payment payment) {
        return new JpaPaymentEntity(
                payment.getId(),
                payment.getUserId(),
                payment.getPaymentMethodId(),
                payment.getTotalAmount(),
                payment.getPaidAt(),
                payment.getCreatedAt()
        );
    }

    public Payment toEntity() {
        return Payment.with(
                this.id,
                this.userId,
                this.paymentMethodId,
                this.totalAmount,
                this.paidAt,
                this.createdAt
        );
    }
}
