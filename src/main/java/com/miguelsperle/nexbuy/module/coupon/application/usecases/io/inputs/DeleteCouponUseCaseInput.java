package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs;

public record DeleteCouponUseCaseInput(
        String couponId
) {
    public static DeleteCouponUseCaseInput with(String couponId) {
        return new DeleteCouponUseCaseInput(couponId);
    }
}
