package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs;

import java.math.BigDecimal;

public record ApplyCouponUseCaseOutput(
        Integer discountPercentage,
        BigDecimal totalAmountWithDiscount
) {
    public static ApplyCouponUseCaseOutput from(Integer discountPercentage, BigDecimal totalAmountWithDiscount) {
        return new ApplyCouponUseCaseOutput(discountPercentage, totalAmountWithDiscount);
    }
}
