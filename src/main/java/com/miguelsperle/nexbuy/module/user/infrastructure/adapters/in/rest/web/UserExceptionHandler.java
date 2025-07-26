package com.miguelsperle.nexbuy.module.user.infrastructure.adapters.in.rest.web;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.rest.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(userAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(NaturalPersonAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleNaturalPersonAlreadyExistsException(NaturalPersonAlreadyExistsException naturalPersonAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(naturalPersonAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(NaturalPersonNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNaturalPersonNotFoundException(NaturalPersonNotFoundException naturalPersonNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(naturalPersonNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(LegalPersonAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleLegalPersonAlreadyExistsException(LegalPersonAlreadyExistsException legalPersonAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(legalPersonAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(LegalPersonNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleLegalPersonNotFoundException(LegalPersonNotFoundException legalPersonNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(legalPersonNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserNotVerifiedException(UserNotVerifiedException userNotVerifiedException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorMessageResponse.from(
                Collections.singletonList(userNotVerifiedException.getMessage()), HttpStatus.FORBIDDEN.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserAlreadyVerifiedException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException userAlreadyVerifiedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(userAlreadyVerifiedException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessageResponse> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorMessageResponse.from(
                Collections.singletonList(invalidCredentialsException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserCodeNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserCodeNotFoundException(UserCodeNotFoundException userCodeNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(userCodeNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserCodeExpiredException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserCodeExpiredException(UserCodeExpiredException userCodeExpiredException) {
        return ResponseEntity.status(HttpStatus.GONE).body(ErrorMessageResponse.from(
                Collections.singletonList(userCodeExpiredException.getMessage()), HttpStatus.GONE.getReasonPhrase()
        ));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(refreshTokenNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<ErrorMessageResponse> handleRefreshTokenExpiredException(RefreshTokenExpiredException refreshTokenExpiredException) {
        return ResponseEntity.status(HttpStatus.GONE).body(ErrorMessageResponse.from(
                Collections.singletonList(refreshTokenExpiredException.getMessage()), HttpStatus.GONE.getReasonPhrase()
        ));
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<ErrorMessageResponse> handleInvalidCurrentPasswordException(InvalidCurrentPasswordException invalidCurrentPasswordException) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ErrorMessageResponse.from(
                Collections.singletonList(invalidCurrentPasswordException.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()
        ));
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleAddressNotFoundException(AddressNotFoundException addressNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(addressNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }
}
