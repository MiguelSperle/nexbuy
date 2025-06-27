package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class ProductCategoryAlreadyExistsException extends RuntimeException {
  public ProductCategoryAlreadyExistsException(String message) {
    super(message);
  }
}
