package com.miguelsperle.nexbuy.module.payments.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.payments.domain.entities.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_methods")
@AllArgsConstructor
@NoArgsConstructor
public class JpaPaymentMethodEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaPaymentMethodEntity from(PaymentMethod paymentMethod) {
        return new JpaPaymentMethodEntity(
                paymentMethod.getId(),
                paymentMethod.getName(),
                paymentMethod.getCreatedAt()
        );
    }

    public PaymentMethod toEntity() {
        return PaymentMethod.with(
                this.id,
                this.name,
                this.createdAt
        );
    }
}
