package com.miguelsperle.nexbuy.module.coupon.infrastructure.adapters.in.web.dtos.responses;

import com.miguelsperle.nexbuy.module.coupon.application.usecases.io.outputs.ApplyCouponUseCaseOutput;

import java.math.BigDecimal;

public record ApplyCouponResponse(
        Integer discountPercentage,
        BigDecimal amountWithDiscount
) {
    public static ApplyCouponResponse from(ApplyCouponUseCaseOutput applyCouponUseCaseOutput) {
        return new ApplyCouponResponse(
                applyCouponUseCaseOutput.discountPercentage(),
                applyCouponUseCaseOutput.amountWithDiscount()
        );
    }
}
