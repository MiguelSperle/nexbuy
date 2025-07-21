package com.miguelsperle.nexbuy.core.application.exceptions;

public class AuthenticatedUserIdNotFoundException extends RuntimeException {
  public AuthenticatedUserIdNotFoundException(String message) {
    super(message);
  }
}
