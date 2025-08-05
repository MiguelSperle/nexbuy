package com.miguelsperle.nexbuy.shared.infrastructure.adapters.exceptions;

public class JwtTokenCreationFailedException extends RuntimeException {
    public JwtTokenCreationFailedException(String message) {
        super(message);
    }

    public static JwtTokenCreationFailedException with(String message) {
        return new JwtTokenCreationFailedException(message);
    }
}
