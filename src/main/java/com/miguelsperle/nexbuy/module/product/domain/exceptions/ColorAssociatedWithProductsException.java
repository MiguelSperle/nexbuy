package com.miguelsperle.nexbuy.module.product.domain.exceptions;

public class ColorAssociatedWithProductsException extends RuntimeException {
    public ColorAssociatedWithProductsException(String message) {
        super(message);
    }

    public static ColorAssociatedWithProductsException with(String message) {
        return new ColorAssociatedWithProductsException(message);
    }
}
