package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs;

public record ActivateCouponUseCaseInput(
        String couponId
) {
    public static ActivateCouponUseCaseInput with(String couponId) {
        return new ActivateCouponUseCaseInput(couponId);
    }
}
