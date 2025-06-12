package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class InvalidPrincipalTypeException extends RuntimeException {
  public InvalidPrincipalTypeException(String message) {
    super(message);
  }
}
