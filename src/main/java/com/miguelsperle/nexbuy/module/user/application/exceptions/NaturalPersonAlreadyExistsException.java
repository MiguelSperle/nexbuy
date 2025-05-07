package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class NaturalPersonAlreadyExistsException extends RuntimeException {
    public NaturalPersonAlreadyExistsException(String message) {
        super(message);
    }
}
