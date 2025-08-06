package com.miguelsperle.nexbuy.module.coupon.application.usecases.io.inputs;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateCouponUseCaseInput(
        String code,
        Integer percentage,
        BigDecimal minimumPurchaseAmount,
        String couponType,
        Integer usageLimit,
        Boolean isActive,
        LocalDateTime expiresIn
) {
    public static CreateCouponUseCaseInput with(
            String code,
            Integer percentage,
            BigDecimal minimumPurchaseAmount,
            String couponType,
            Integer usageLimit,
            Boolean isActive,
            LocalDateTime expiresIn
    ) {
        return new CreateCouponUseCaseInput(
                code,
                percentage,
                minimumPurchaseAmount,
                couponType,
                usageLimit,
                isActive,
                expiresIn
        );
    }
}
