package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class InvalidCodeLengthException extends RuntimeException {
    public InvalidCodeLengthException(String message) {
        super(message);
    }
}
