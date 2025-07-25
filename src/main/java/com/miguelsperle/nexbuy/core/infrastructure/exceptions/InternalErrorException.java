package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String message) {
        super(message);
    }

    public static InternalErrorException with(String message) {
        return new InternalErrorException(message);
    }
}
