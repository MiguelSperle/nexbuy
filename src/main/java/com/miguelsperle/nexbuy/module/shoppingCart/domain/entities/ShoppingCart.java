package com.miguelsperle.nexbuy.module.shoppingCart.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShoppingCart {
    private final String id;
    private final String userId;
    private final BigDecimal totalAmount;
    private final LocalDateTime createdAt;

    private ShoppingCart(
            String id,
            String userId,
            BigDecimal totalAmount,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    public static ShoppingCart newShoppingCart(String userId) {
        return new ShoppingCart(
                IdentifierUtils.generateUUID(),
                userId,
                BigDecimal.ZERO,
                TimeUtils.now()
        );
    }

    public static ShoppingCart with(
            String id,
            String userId,
            BigDecimal totalAmount,
            LocalDateTime createdAt
    ) {
        return new ShoppingCart(
                id,
                userId,
                totalAmount,
                createdAt
        );
    }

    public ShoppingCart withTotalAmount(BigDecimal totalAmount) {
        return new ShoppingCart(
                this.id,
                this.userId,
                totalAmount,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id='" + this.id + '\'' +
                ", userId='" + this.userId + '\'' +
                ", totalAmount=" + this.totalAmount +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
