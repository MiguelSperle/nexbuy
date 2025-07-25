package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class JwtTokenValidationFailedException extends RuntimeException {
    public JwtTokenValidationFailedException(String message) {
        super(message);
    }

    public static JwtTokenValidationFailedException with(String message) {
        return new JwtTokenValidationFailedException(message);
    }
}
