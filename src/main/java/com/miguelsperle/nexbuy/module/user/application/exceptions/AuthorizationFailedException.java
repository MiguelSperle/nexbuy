package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class AuthorizationFailedException extends RuntimeException {
  public AuthorizationFailedException(String message) {
    super(message);
  }
}
