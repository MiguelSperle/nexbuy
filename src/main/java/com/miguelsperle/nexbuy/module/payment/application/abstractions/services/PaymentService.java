package com.miguelsperle.nexbuy.module.payment.application.abstractions.services;

public interface PaymentService {
    String createCheckoutSession(String paymentId, long totalAmountInCents);
}
