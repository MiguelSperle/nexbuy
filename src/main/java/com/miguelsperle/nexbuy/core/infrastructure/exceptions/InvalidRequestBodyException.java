package com.miguelsperle.nexbuy.core.infrastructure.exceptions;

import lombok.Getter;

import java.util.Map;

@Getter
public class InvalidRequestBodyException extends RuntimeException {
    private final Map<String, String> fieldErrors;

    public InvalidRequestBodyException(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
