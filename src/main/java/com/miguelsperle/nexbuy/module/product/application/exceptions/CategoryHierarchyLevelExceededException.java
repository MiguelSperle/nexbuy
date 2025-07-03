package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class CategoryHierarchyLevelExceededException extends RuntimeException {
    public CategoryHierarchyLevelExceededException(String message) {
        super(message);
    }
}
