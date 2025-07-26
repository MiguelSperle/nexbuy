package com.miguelsperle.nexbuy.core.infrastructure.configuration.exceptionHandler;

import com.miguelsperle.nexbuy.core.application.exceptions.MissingComplementException;
import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.JwtTokenCreationFailedException;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.JwtTokenValidationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNoResourceFoundException(NoResourceFoundException noResourceFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList("Resource not found"), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(JwtTokenCreationFailedException.class)
    public ResponseEntity<ErrorMessageResponse> handleJwtTokenCreationFailedException(JwtTokenCreationFailedException jwtTokenCreationFailedException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorMessageResponse.from(
                Collections.singletonList(jwtTokenCreationFailedException.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(JwtTokenValidationFailedException.class)
    public ResponseEntity<ErrorMessageResponse> handleJwtTokenValidationFailedException(JwtTokenValidationFailedException jwtTokenValidationFailedException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessageResponse.from(
                Collections.singletonList(jwtTokenValidationFailedException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> handleException(Exception exception) {
        log.error("Handling unexpected exception", exception);
        return ResponseEntity.internalServerError().body(ErrorMessageResponse.from(
                Collections.singletonList("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(MissingComplementException.class)
    public ResponseEntity<ErrorMessageResponse> handleMissingComplementException(MissingComplementException missingComplementException) {
        return ResponseEntity.badRequest().body(ErrorMessageResponse.from(
                Collections.singletonList(missingComplementException.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }
}
