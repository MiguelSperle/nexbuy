package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class JwtTokenCreationFailedException extends RuntimeException {
    public JwtTokenCreationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
