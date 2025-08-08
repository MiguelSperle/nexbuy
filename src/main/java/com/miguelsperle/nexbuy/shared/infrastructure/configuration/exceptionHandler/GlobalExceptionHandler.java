package com.miguelsperle.nexbuy.shared.infrastructure.configuration.exceptionHandler;

import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.dtos.responses.ErrorMessageResponse;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.exceptions.JwtTokenCreationFailedException;
import com.miguelsperle.nexbuy.shared.infrastructure.adapters.exceptions.JwtTokenValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Order
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.badRequest().body(ErrorMessageResponse.from(errors, HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }

    @ExceptionHandler(JwtTokenCreationFailedException.class)
    public ResponseEntity<ErrorMessageResponse> handleJwtTokenCreationFailedException(JwtTokenCreationFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessageResponse.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(JwtTokenValidationFailedException.class)
    public ResponseEntity<ErrorMessageResponse> handleJwtTokenValidationFailedException(JwtTokenValidationFailedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessageResponse.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> handleException(Exception ex) {
        log.error("Handling unexpected exception", ex);
        return ResponseEntity.internalServerError().body(ErrorMessageResponse.from(
                Collections.singletonList("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorMessageResponse> handleDomainException(DomainException ex) {
        return ResponseEntity.status(HttpStatus.valueOf(ex.getStatusCode())).body(ErrorMessageResponse.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.valueOf(ex.getStatusCode()).getReasonPhrase()
        ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(ex.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }
}
