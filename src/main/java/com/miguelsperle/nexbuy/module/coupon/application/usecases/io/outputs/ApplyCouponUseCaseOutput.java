package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs;

import java.math.BigDecimal;

public record ApplyCouponUseCaseOutput(
        BigDecimal totalAmount,
        Integer discountPercentage,
        BigDecimal finalAmount
) {
    public static ApplyCouponUseCaseOutput from(BigDecimal totalAmount, Integer discountPercentage, BigDecimal finalAmount) {
        return new ApplyCouponUseCaseOutput(totalAmount, discountPercentage, finalAmount);
    }
}
