package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class RefreshTokenNotFoundException extends RuntimeException {
  public RefreshTokenNotFoundException(String message) {
    super(message);
  }
}
