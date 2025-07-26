package com.miguelsperle.nexbuy.module.product.domain.exceptions;

public class ProductStatusNotAllowedException extends RuntimeException {
    public ProductStatusNotAllowedException(String message) {
        super(message);
    }

    public static ProductStatusNotAllowedException with(String message) {
        return new ProductStatusNotAllowedException(message);
    }
}
