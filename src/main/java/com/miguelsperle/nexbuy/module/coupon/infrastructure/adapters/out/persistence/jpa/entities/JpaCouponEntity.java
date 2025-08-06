package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.entities;

import com.miguelsperle.nexbuy.module.coupon.domain.entities.Coupon;
import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
public class JpaCouponEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String code;

    @Column(nullable = false)
    private Integer percentage;

    @Column(name = "minimum_purchase_amount", precision = 19, scale = 2)
    private BigDecimal minimumPurchaseAmount;

    @Column(name = "type", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private CouponType couponType;

    @Column(name = "times_used", nullable = false)
    private Integer timesUsed;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "expires_in", nullable = false)
    private LocalDateTime expiresIn;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static JpaCouponEntity from(Coupon coupon) {
        return new JpaCouponEntity(
                coupon.getId(),
                coupon.getCode(),
                coupon.getPercentage(),
                coupon.getMinimumPurchaseAmount(),
                coupon.getCouponType(),
                coupon.getTimesUsed(),
                coupon.getUsageLimit(),
                coupon.getIsActive(),
                coupon.getExpiresIn(),
                coupon.getCreatedAt()
        );
    }

    public Coupon toEntity() {
        return Coupon.with(
                this.id,
                this.code,
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
}
