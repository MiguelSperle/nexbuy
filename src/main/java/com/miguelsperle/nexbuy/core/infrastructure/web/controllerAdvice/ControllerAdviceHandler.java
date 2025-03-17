package com.miguelsperle.nexbuy.core.infrastructure.web.controllerAdvice;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.InvalidRequestBodyResponse;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.InvalidRequestBodyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceHandler {
    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<Object> handleInvalidRequestBodyException(InvalidRequestBodyException invalidRequestBodyException) {
        return ResponseEntity.badRequest().body(new InvalidRequestBodyResponse(invalidRequestBodyException.getFieldErrors(), HttpStatus.BAD_REQUEST.value()));
    }
}
