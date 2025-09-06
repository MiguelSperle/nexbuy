package com.miguelsperle.nexbuy.module.payment.application.ports.out.services;

import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentMethod;
import com.miguelsperle.nexbuy.module.payment.domain.payment.PaymentGatewayInfo;

public interface PaymentService {
    PaymentGatewayInfo createCheckoutSession(String paymentId, long totalAmountInCents, PaymentMethod paymentMethod);
}
