package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.services.exceptions;

public class PaymentFailedException extends RuntimeException {
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static PaymentFailedException with(String message, Throwable cause) {
        return new PaymentFailedException(message, cause);
    }
}
