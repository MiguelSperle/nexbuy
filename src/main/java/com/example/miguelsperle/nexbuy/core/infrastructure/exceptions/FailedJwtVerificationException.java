package com.example.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class FailedJwtVerificationException extends RuntimeException {
    public FailedJwtVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
