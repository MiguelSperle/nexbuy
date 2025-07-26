package com.miguelsperle.nexbuy.module.product.domain.exceptions;

public class BrandAssociatedWithProductsException extends RuntimeException {
    public BrandAssociatedWithProductsException(String message) {
        super(message);
    }

    public static BrandAssociatedWithProductsException with(String message) {
        return new BrandAssociatedWithProductsException(message);
    }
}
