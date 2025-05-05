package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class UserVerificationCodeNotFoundException extends RuntimeException {
  public UserVerificationCodeNotFoundException(String message) {
    super(message);
  }
}
