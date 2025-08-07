package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs;

public record DeactivateCouponUseCaseInput(
        String couponId
) {
    public static DeactivateCouponUseCaseInput with(String couponId) {
        return new DeactivateCouponUseCaseInput(couponId);
    }
}
