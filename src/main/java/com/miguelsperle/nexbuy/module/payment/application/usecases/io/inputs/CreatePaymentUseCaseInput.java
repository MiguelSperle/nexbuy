package com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs;

import java.math.BigDecimal;

public record CreatePaymentUseCaseInput(
        String orderId,
        BigDecimal totalAmount
) {
    public static CreatePaymentUseCaseInput with(
            String orderId,
            BigDecimal totalAmount
    ) {
        return new CreatePaymentUseCaseInput(
                orderId,
                totalAmount
        );
    }
}
