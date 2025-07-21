package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class LegalPersonNotFoundException extends RuntimeException {
  public LegalPersonNotFoundException(String message) {
    super(message);
  }

  public static LegalPersonNotFoundException with(String message) {
    return new LegalPersonNotFoundException(message);
  }
}
