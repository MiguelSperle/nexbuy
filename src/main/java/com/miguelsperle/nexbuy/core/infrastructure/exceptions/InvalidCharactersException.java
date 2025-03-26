package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class InvalidCharactersException extends RuntimeException {
    public InvalidCharactersException(String message) {
        super(message);
    }
}
