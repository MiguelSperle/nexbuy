package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class LegalPersonNotFoundException extends RuntimeException {
  public LegalPersonNotFoundException(String message) {
    super(message);
  }
}
