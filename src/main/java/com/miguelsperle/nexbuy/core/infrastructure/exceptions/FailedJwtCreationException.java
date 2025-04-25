package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class FailedJwtCreationException extends RuntimeException {
    public FailedJwtCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
