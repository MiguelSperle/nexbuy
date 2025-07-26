package com.miguelsperle.nexbuy.module.product.domain.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException with(String message) {
        return new ProductNotFoundException(message);
    }
}
