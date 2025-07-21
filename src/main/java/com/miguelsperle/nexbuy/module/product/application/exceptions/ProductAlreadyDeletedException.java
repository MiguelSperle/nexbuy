package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ProductAlreadyDeletedException extends RuntimeException {
    public ProductAlreadyDeletedException(String message) {
        super(message);
    }

    public static ProductAlreadyDeletedException with(String message) {
        return new ProductAlreadyDeletedException(message);
    }
}
