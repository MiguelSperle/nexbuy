package com.miguelsperle.nexbuy.common.infrastructure.adapters.out.services.exceptions;

public class JwtTokenCreationFailedException extends RuntimeException {
    public JwtTokenCreationFailedException(String message) {
        super(message);
    }

    public static JwtTokenCreationFailedException with(String message) {
        return new JwtTokenCreationFailedException(message);
    }
}
