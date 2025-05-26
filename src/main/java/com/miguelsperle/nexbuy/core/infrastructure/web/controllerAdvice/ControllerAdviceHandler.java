package com.miguelsperle.nexbuy.core.infrastructure.web.controllerAdvice;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.JwtTokenValidationFailedException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ControllerAdviceHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerAdviceHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        final List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                errors, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException() {
        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                Collections.singletonList("Invalid request body"), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(JwtTokenValidationFailedException.class)
    public ResponseEntity<Object> handleJwtTokenValidationFailedException(JwtTokenValidationFailedException jwtTokenValidationFailedException) {
        log.warn("Handling Jwt token validation failed exception - ExceptionMessage: [{}]", jwtTokenValidationFailedException.getMessage(), jwtTokenValidationFailedException);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageResponse(
                Collections.singletonList(jwtTokenValidationFailedException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        log.error("Handling unexpected exception - ExceptionMessage: [{}]", exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(new ErrorMessageResponse(
                Collections.singletonList("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value()
        ));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(userAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(NaturalPersonAlreadyExistsException.class)
    public ResponseEntity<Object> handleNaturalPersonAlreadyExistsException(NaturalPersonAlreadyExistsException naturalPersonAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(naturalPersonAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(LegalPersonAlreadyExistsException.class)
    public ResponseEntity<Object> handleLegalPersonAlreadyExistsException(LegalPersonAlreadyExistsException legalPersonAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(legalPersonAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(MissingRequiredComplementException.class)
    public ResponseEntity<Object> handleMissingRequiredDataException(MissingRequiredComplementException missingRequiredComplementException) {
        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                Collections.singletonList(missingRequiredComplementException.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<Object> handleUserNotVerifiedException(UserNotVerifiedException userNotVerifiedException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageResponse(
                Collections.singletonList(userNotVerifiedException.getMessage()), HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value()
        ));
    }

    @ExceptionHandler(UserAlreadyVerifiedException.class)
    public ResponseEntity<Object> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException userAlreadyVerifiedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(userAlreadyVerifiedException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageResponse(
                Collections.singletonList(invalidCredentialsException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                Collections.singletonList(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(UserCodeNotFoundException.class)
    public ResponseEntity<Object> handleUserCodeNotFoundException(UserCodeNotFoundException userCodeNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                Collections.singletonList(userCodeNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(UserCodeExpiredException.class)
    public ResponseEntity<Object> handleUserCodeExpiredException(UserCodeExpiredException userCodeExpiredException) {
        return ResponseEntity.status(HttpStatus.GONE).body(new ErrorMessageResponse(
                Collections.singletonList(userCodeExpiredException.getMessage()), HttpStatus.GONE.getReasonPhrase(), HttpStatus.GONE.value()
        ));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<Object> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                Collections.singletonList(refreshTokenNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<Object> handleRefreshTokenExpiredException(RefreshTokenExpiredException refreshTokenExpiredException) {
        return ResponseEntity.status(HttpStatus.GONE).body(new ErrorMessageResponse(
                Collections.singletonList(refreshTokenExpiredException.getMessage()), HttpStatus.GONE.getReasonPhrase(), HttpStatus.GONE.value()
        ));
    }
}
