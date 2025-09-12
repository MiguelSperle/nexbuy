package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.out.services.exceptions;

public class FailedToCreateCheckoutSessionException extends RuntimeException {
    public FailedToCreateCheckoutSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FailedToCreateCheckoutSessionException with(String message, Throwable cause) {
        return new FailedToCreateCheckoutSessionException(message, cause);
    }
}
