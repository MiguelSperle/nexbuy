package com.miguelsperle.nexbuy.shared.application.exceptions;

public class MissingComplementException extends RuntimeException {
    public MissingComplementException(String message) {
        super(message);
    }

    public static MissingComplementException with(String message) {
        return new MissingComplementException(message);
    }
}
