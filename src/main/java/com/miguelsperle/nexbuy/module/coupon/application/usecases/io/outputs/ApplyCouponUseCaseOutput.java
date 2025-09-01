package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs;

import java.math.BigDecimal;

public record ApplyCouponUseCaseOutput(
        Integer discountPercentage,
        BigDecimal amountWithDiscount
) {
    public static ApplyCouponUseCaseOutput from(Integer discountPercentage, BigDecimal finalAmount) {
        return new ApplyCouponUseCaseOutput(discountPercentage, finalAmount);
    }
}
