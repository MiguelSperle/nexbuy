package com.miguelsperle.nexbuy.shared.infrastructure.services.exceptions;

public class JwtTokenDecodingFailedException extends RuntimeException {
    public JwtTokenDecodingFailedException(String message) {
        super(message);
    }

    public static JwtTokenDecodingFailedException with(String message) {
        return new JwtTokenDecodingFailedException(message);
    }
}
