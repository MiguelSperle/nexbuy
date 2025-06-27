package com.miguelsperle.nexbuy.module.product.application.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {
  public CategoryAlreadyExistsException(String message) {
    super(message);
  }
}
