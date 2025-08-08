package com.miguelsperle.nexbuy.shared.domain.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException with(String message) {
        return new NotFoundException(message);
    }
}
