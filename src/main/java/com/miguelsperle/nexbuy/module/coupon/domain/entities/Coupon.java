package com.miguelsperle.nexbuy.module.coupon.domain.entities;

import com.miguelsperle.nexbuy.core.domain.utils.IdentifierUtils;
import com.miguelsperle.nexbuy.core.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Coupon {
    private final String id;
    private final String code;
    private final Integer percentage;
    private final BigDecimal minimumPurchaseAmount;
    private final CouponType couponType;
    private final Integer timesUsed;
    private final Integer usageLimit;
    private final Boolean isActive;
    private final LocalDateTime expiresIn;
    private final LocalDateTime createdAt;

    private Coupon(
            String id,
            String code,
            Integer percentage,
            BigDecimal minimumPurchaseAmount,
            CouponType couponType,
            Integer timesUsed,
            Integer usageLimit,
            Boolean isActive,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.code = code;
        this.percentage = percentage;
        this.minimumPurchaseAmount = minimumPurchaseAmount;
        this.couponType = couponType;
        this.timesUsed = timesUsed;
        this.usageLimit = usageLimit;
        this.isActive = isActive;
        this.expiresIn = expiresIn;
        this.createdAt = createdAt;
    }

    public static Coupon newCoupon(
            String code,
            Integer percentage,
            BigDecimal minimumPurchaseAmount,
            CouponType couponType,
            Integer usageLimit,
            Boolean isActive,
            LocalDateTime expiresIn
    ) {
        return new Coupon(
                IdentifierUtils.generateUUID(),
                code,
                percentage,
                minimumPurchaseAmount,
                couponType,
                0,
                usageLimit,
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
            CouponType couponType,
            Integer timesUsed,
            Integer usageLimit,
            Boolean isActive,
            LocalDateTime expiresIn,
            LocalDateTime createdAt
    ) {
        return new Coupon(
                id,
                code,
                percentage,
                minimumPurchaseAmount,
                couponType,
                timesUsed,
                usageLimit,
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
                this.couponType,
                this.timesUsed,
                this.usageLimit,
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
                this.couponType,
                this.timesUsed,
                this.usageLimit,
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
                this.couponType,
                this.timesUsed,
                this.usageLimit,
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
                this.couponType,
                this.timesUsed,
                this.usageLimit,
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
                this.couponType,
                this.timesUsed,
                this.usageLimit,
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

    public CouponType getCouponType() {
        return this.couponType;
    }

    public Integer getTimesUsed() {
        return this.timesUsed;
    }

    public Integer getUsageLimit() {
        return this.usageLimit;
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
                ", couponType=" + this.couponType +
                ", timesUsed=" + this.timesUsed +
                ", usageLimit=" + this.usageLimit +
                ", isActive=" + this.isActive +
                ", expiresIn=" + this.expiresIn +
                ", createdAt=" + this.createdAt +
                '}';
    }
}
