package com.miguelsperle.nexbuy.module.user.domain.exceptions;

public class NaturalPersonAlreadyExistsException extends RuntimeException {
    public NaturalPersonAlreadyExistsException(String message) {
        super(message);
    }

    public static NaturalPersonAlreadyExistsException with(String message) {
        return new NaturalPersonAlreadyExistsException(message);
    }
}
