package com.miguelsperle.nexbuy.module.coupon.infrastructure.rest.dtos.res;

import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.ApplyCouponUseCaseOutput;

import java.math.BigDecimal;

public record ApplyCouponResponse(
        Integer discountPercentage,
        BigDecimal totalAmountWithDiscount
) {
    public static ApplyCouponResponse from(ApplyCouponUseCaseOutput applyCouponUseCaseOutput) {
        return new ApplyCouponResponse(
                applyCouponUseCaseOutput.discountPercentage(),
                applyCouponUseCaseOutput.totalAmountWithDiscount()
        );
    }
}
