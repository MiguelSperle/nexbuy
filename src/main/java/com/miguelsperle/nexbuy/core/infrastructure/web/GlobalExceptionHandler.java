package com.miguelsperle.nexbuy.core.infrastructure.web;

import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.JwtTokenValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Order
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        final List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.badRequest().body(ErrorMessageResponse.from(errors, HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageResponse> handleHttpMessageNotReadableException() {
        return ResponseEntity.badRequest().body(ErrorMessageResponse.from(
                Collections.singletonList("Invalid request body"), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNoResourceFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList("Resource not found"), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(JwtTokenValidationFailedException.class)
    public ResponseEntity<ErrorMessageResponse> handleJwtTokenValidationFailedException(JwtTokenValidationFailedException jwtTokenValidationFailedException) {
        log.warn("Handling Jwt token validation failed exception - ExceptionMessage: [{}]", jwtTokenValidationFailedException.getMessage(), jwtTokenValidationFailedException);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessageResponse.from(
                Collections.singletonList(jwtTokenValidationFailedException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> handleException(Exception exception) {
        log.error("Handling unexpected exception - ExceptionMessage: [{}]", exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(ErrorMessageResponse.from(
                Collections.singletonList("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(MissingRequiredComplementException.class)
    public ResponseEntity<ErrorMessageResponse> handleMissingRequiredComplementException(MissingRequiredComplementException missingRequiredComplementException) {
        return ResponseEntity.badRequest().body(ErrorMessageResponse.from(
                Collections.singletonList(missingRequiredComplementException.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }
}
