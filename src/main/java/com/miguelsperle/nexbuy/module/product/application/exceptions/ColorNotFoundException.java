package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ColorNotFoundException extends RuntimeException {
    public ColorNotFoundException(String message) {
        super(message);
    }

    public static ColorNotFoundException with(String message) {
        return new ColorNotFoundException(message);
    }
}
