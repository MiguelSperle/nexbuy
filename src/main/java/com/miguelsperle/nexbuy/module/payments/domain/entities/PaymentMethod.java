package com.miguelsperle.nexbuy.module.payments.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.time.LocalDateTime;

public class PaymentMethod {
    private final String id;
    private final String name;
    private final LocalDateTime createdAt;

    private PaymentMethod(
            String id,
            String name,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static PaymentMethod newPaymentMethod(String name) {
        return new PaymentMethod(
                IdentifierUtils.generateUUID(),
                name,
                TimeUtils.now()
        );
    }

    public static PaymentMethod with(
            String id,
            String name,
            LocalDateTime createdAt
    ) {
        return new PaymentMethod(
                id,
                name,
                createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id='" + this.id + '\'' +
                ", name='" + this.name + '\'' +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
