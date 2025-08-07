package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UpdateCouponUseCaseInput(
        String couponId,
        String code,
        Integer percentage,
        BigDecimal minimumPurchaseAmount,
        LocalDateTime expiresIn
) {
    public static UpdateCouponUseCaseInput with(
            String couponId,
            String code,
            Integer percentage,
            BigDecimal minimumPurchaseAmount,
            LocalDateTime expiresIn
    ) {
        return new UpdateCouponUseCaseInput(
                couponId,
                code,
                percentage,
                minimumPurchaseAmount,
                expiresIn
        );
    }
}
