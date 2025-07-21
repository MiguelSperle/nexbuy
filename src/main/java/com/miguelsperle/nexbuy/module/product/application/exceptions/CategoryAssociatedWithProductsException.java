package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class CategoryAssociatedWithProductsException extends RuntimeException {
    public CategoryAssociatedWithProductsException(String message) {
        super(message);
    }

    public static CategoryAssociatedWithProductsException with(String message) {
        return new CategoryAssociatedWithProductsException(message);
    }
}
