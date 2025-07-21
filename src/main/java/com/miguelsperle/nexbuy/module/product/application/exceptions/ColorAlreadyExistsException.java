package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ColorAlreadyExistsException extends RuntimeException {
    public ColorAlreadyExistsException(String message) {
        super(message);
    }

    public static ColorAlreadyExistsException with(String message) {
        return new ColorAlreadyExistsException(message);
    }
}
