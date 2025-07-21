package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class LegalPersonAlreadyExistsException extends RuntimeException {
    public LegalPersonAlreadyExistsException(String message) {
        super(message);
    }

    public static LegalPersonAlreadyExistsException with(String message) {
        return new LegalPersonAlreadyExistsException(message);
    }
}
