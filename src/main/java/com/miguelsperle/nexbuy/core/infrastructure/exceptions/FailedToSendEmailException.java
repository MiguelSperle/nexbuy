package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class FailedToSendEmailException extends RuntimeException {
    public FailedToSendEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
