package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.specifications;

import com.miguelsperle.nexbuy.module.coupon.domain.enums.CouponType;
import com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.out.persistence.jpa.entities.JpaCouponEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class JpaCouponSpecification {
    public static Specification<JpaCouponEntity> filterByPercentage(int percentage) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("percentage"), percentage);
    }

    public static Specification<JpaCouponEntity> filterByMinimumPurchaseAmount(BigDecimal minimumPurchaseAmount) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("minimumPurchaseAmount"), minimumPurchaseAmount);
    }

    public static Specification<JpaCouponEntity> filterByType(CouponType couponType) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("couponType"), couponType);
    }

    public static Specification<JpaCouponEntity> filterByIsActive(boolean isActive) {
        return (root, query, criterialBuilder) ->
                criterialBuilder.equal(root.get("isActive"), isActive);
    }
}
