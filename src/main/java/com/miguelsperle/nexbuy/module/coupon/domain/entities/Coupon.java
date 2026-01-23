package com.miguelsperle.nexbuy.module.coupon.domain.entities;

import com.miguelsperle.nexbuy.shared.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Coupon {
    private final String id;
    private final String code;
    private final Integer percentage;
    private final BigDecimal minimumPurchaseAmount;
    private final Boolean isActive;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private Coupon(
            String id,
            String code,
            Integer percentage,
            BigDecimal minimumPurchaseAmount,
            Boolean isActive,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.code = code;
        this.percentage = percentage;
        this.minimumPurchaseAmount = minimumPurchaseAmount;
        this.isActive = isActive;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static Coupon newCoupon(
            String code,
            Integer percentage,
            BigDecimal minimumPurchaseAmount,
            Boolean isActive,
            LocalDateTime expiresIn
    ) {
        return new Coupon(
                IdentifierUtils.generateUUID(),
                code,
                percentage,
                minimumPurchaseAmount,
                isActive,
                expiresIn,
                TimeUtils.now()
        );
    }

    public static Coupon with(
            String id,
            String code,
            Integer percentage,
            BigDecimal minimumPurchaseAmount,
            Boolean isActive,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new Coupon(
                id,
                code,
                percentage,
                minimumPurchaseAmount,
                isActive,
                expiresIn,
                createdAt
        );
    }

    public Coupon withCode(String code) {
        return new Coupon(
                this.id,
                code,
                this.percentage,
                this.minimumPurchaseAmount,
                this.isActive,
                this.expiresIn,
                this.createdAt
        );
    }

    public Coupon withPercentage(Integer percentage) {
        return new Coupon(
                this.id,
                this.code,
                percentage,
                this.minimumPurchaseAmount,
                this.isActive,
                this.expiresIn,
                this.createdAt
        );
    }

    public Coupon withMinimumPurchaseAmount(BigDecimal minimumPurchaseAmount) {
        return new Coupon(
                this.id,
                this.code,
                this.percentage,
                minimumPurchaseAmount,
                this.isActive,
                this.expiresIn,
                this.createdAt
        );
    }

    public Coupon withIsActive(Boolean isActive) {
        return new Coupon(
                this.id,
                this.code,
                this.percentage,
                this.minimumPurchaseAmount,
                isActive,
                this.expiresIn,
                this.createdAt
        );
    }

    public Coupon withExpiresIn(LocalDateTime expiresIn) {
        return new Coupon(
                this.id,
                this.code,
                this.percentage,
                this.minimumPurchaseAmount,
                this.isActive,
                expiresIn,
                this.createdAt
        );
    }

    public String getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public Integer getPercentage() {
        return this.percentage;
    }

    public BigDecimal getMinimumPurchaseAmount() {
        return this.minimumPurchaseAmount;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public LocalDateTime getExpiresIn() {
        return this.expiresIn;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id='" + this.id + '\'' +
                ", code='" + this.code + '\'' +
                ", percentage=" + this.percentage +
                ", minimumPurchaseAmount=" + this.minimumPurchaseAmount +
                ", isActive=" + this.isActive +
                ", expiresIn=" + this.expiresIn +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
