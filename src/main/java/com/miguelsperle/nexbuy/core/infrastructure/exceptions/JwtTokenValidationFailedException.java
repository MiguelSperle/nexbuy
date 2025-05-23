package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class JwtTokenValidationFailedException extends RuntimeException {
    public JwtTokenValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
