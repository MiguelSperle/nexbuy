package com.miguelsperle.nexbuy.module.payment.application.ports.out.services;

public interface PaymentService {
    String createCheckoutSession(String paymentId, long totalAmountInCents);
}
