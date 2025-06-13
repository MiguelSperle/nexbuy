package com.miguelsperle.nexbuy.core.application.exceptions;

public class AuthenticatedUserNotFoundException extends RuntimeException {
  public AuthenticatedUserNotFoundException(String message) {
    super(message);
  }
}
