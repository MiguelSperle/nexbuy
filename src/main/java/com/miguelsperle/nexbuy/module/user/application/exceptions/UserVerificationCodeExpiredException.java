package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class UserVerificationCodeExpiredException extends RuntimeException {
  public UserVerificationCodeExpiredException(String message) {
    super(message);
  }
}
