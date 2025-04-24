package com.miguelsperle.nexbuy.core.application.exceptions;

public class MissingRequiredComplementException extends RuntimeException {
    public MissingRequiredComplementException(String message) {
        super(message);
    }
}
