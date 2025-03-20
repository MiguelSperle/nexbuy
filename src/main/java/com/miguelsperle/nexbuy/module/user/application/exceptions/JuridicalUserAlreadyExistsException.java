package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class JuridicalUserAlreadyExistsException extends RuntimeException {
    public JuridicalUserAlreadyExistsException(String message) {
        super(message);
    }
}
