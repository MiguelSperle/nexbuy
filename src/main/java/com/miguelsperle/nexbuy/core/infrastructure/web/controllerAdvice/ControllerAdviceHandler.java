package com.miguelsperle.nexbuy.core.infrastructure.web.controllerAdvice;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.infrastructure.dtos.InvalidRequestBodyResponse;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.InvalidRequestBodyException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.JuridicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.PhysicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(userAlreadyExistsException.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(PhysicalUserAlreadyExistsException.class)
    public ResponseEntity<Object> handlePhysicalUserAlreadyExistsException(PhysicalUserAlreadyExistsException physicalUserAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(physicalUserAlreadyExistsException.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(JuridicalUserAlreadyExistsException.class)
    public ResponseEntity<Object> handleJuridicalUserAlreadyExistsException(JuridicalUserAlreadyExistsException juridicalUserAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(juridicalUserAlreadyExistsException.getMessage(), HttpStatus.CONFLICT.value()));
    }
}
