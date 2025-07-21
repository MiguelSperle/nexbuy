package com.miguelsperle.nexbuy.core.application.exceptions;

public class MissingRequiredComplementException extends RuntimeException {
    public MissingRequiredComplementException(String message) {
        super(message);
    }

    public static MissingRequiredComplementException with(String message) {
        return new MissingRequiredComplementException(message);
    }
}
