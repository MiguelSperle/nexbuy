package com.miguelsperle.nexbuy.module.payment.domain.payment;

public record PaymentGatewayInfo(
        String sessionId,
        String sessionUrl
) {
}
