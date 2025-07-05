package com.miguelsperle.nexbuy.core.application.exceptions;

public class ActionNotAllowedException extends RuntimeException {
  public ActionNotAllowedException(String message) {
    super(message);
  }
}
