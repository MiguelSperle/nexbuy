package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

public class UnexpectedJwtTokenUserIdException extends RuntimeException {
  public UnexpectedJwtTokenUserIdException(String message) {
    super(message);
  }
}
