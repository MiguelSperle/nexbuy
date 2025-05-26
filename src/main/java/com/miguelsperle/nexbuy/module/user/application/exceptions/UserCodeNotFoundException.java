package com.miguelsperle.nexbuy.module.user.application.exceptions;

public class UserCodeNotFoundException extends RuntimeException {
  public UserCodeNotFoundException(String message) {
    super(message);
  }
}
