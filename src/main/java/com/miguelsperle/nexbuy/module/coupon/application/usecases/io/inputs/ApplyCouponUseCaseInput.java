package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs;

import java.math.BigDecimal;

public record ApplyCouponUseCaseInput(
        String code,
        BigDecimal totalAmount
) {
    public static ApplyCouponUseCaseInput with(String code, BigDecimal totalAmount) {
        return new ApplyCouponUseCaseInput(code, totalAmount);
    }
}
