package com.miguelsperle.nexbuy.core.infrastructure.web.controllerAdvice;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.core.infrastructure.exceptions.JwtTokenValidationFailedException;
import com.miguelsperle.nexbuy.module.product.application.exceptions.*;
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

        return ResponseEntity.badRequest().body(new ErrorMessageResponse(errors, HttpStatus.BAD_REQUEST.getReasonPhrase()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException() {
        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                Collections.singletonList("Invalid request body"), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }

    @ExceptionHandler(JwtTokenValidationFailedException.class)
    public ResponseEntity<Object> handleJwtTokenValidationFailedException(JwtTokenValidationFailedException jwtTokenValidationFailedException) {
        log.warn("Handling Jwt token validation failed exception - ExceptionMessage: [{}]", jwtTokenValidationFailedException.getMessage(), jwtTokenValidationFailedException);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageResponse(
                Collections.singletonList(jwtTokenValidationFailedException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        log.error("Handling unexpected exception - ExceptionMessage: [{}]", exception.getMessage(), exception);
        return ResponseEntity.internalServerError().body(new ErrorMessageResponse(
                Collections.singletonList("An unexpected error occurred"), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(userAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(NaturalPersonAlreadyExistsException.class)
    public ResponseEntity<Object> handleNaturalPersonAlreadyExistsException(NaturalPersonAlreadyExistsException naturalPersonAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(naturalPersonAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(LegalPersonAlreadyExistsException.class)
    public ResponseEntity<Object> handleLegalPersonAlreadyExistsException(LegalPersonAlreadyExistsException legalPersonAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(legalPersonAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(MissingRequiredComplementException.class)
    public ResponseEntity<Object> handleMissingRequiredDataException(MissingRequiredComplementException missingRequiredComplementException) {
        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                Collections.singletonList(missingRequiredComplementException.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<Object> handleUserNotVerifiedException(UserNotVerifiedException userNotVerifiedException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageResponse(
                Collections.singletonList(userNotVerifiedException.getMessage()), HttpStatus.FORBIDDEN.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserAlreadyVerifiedException.class)
    public ResponseEntity<Object> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException userAlreadyVerifiedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(userAlreadyVerifiedException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageResponse(
                Collections.singletonList(invalidCredentialsException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                Collections.singletonList(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserCodeNotFoundException.class)
    public ResponseEntity<Object> handleUserCodeNotFoundException(UserCodeNotFoundException userCodeNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                Collections.singletonList(userCodeNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(UserCodeExpiredException.class)
    public ResponseEntity<Object> handleUserCodeExpiredException(UserCodeExpiredException userCodeExpiredException) {
        return ResponseEntity.status(HttpStatus.GONE).body(new ErrorMessageResponse(
                Collections.singletonList(userCodeExpiredException.getMessage()), HttpStatus.GONE.getReasonPhrase()
        ));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<Object> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException refreshTokenNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                Collections.singletonList(refreshTokenNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<Object> handleRefreshTokenExpiredException(RefreshTokenExpiredException refreshTokenExpiredException) {
        return ResponseEntity.status(HttpStatus.GONE).body(new ErrorMessageResponse(
                Collections.singletonList(refreshTokenExpiredException.getMessage()), HttpStatus.GONE.getReasonPhrase()
        ));
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<Object> handleInvalidCurrentPasswordException(InvalidCurrentPasswordException invalidCurrentPasswordException) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorMessageResponse(
                Collections.singletonList(invalidCurrentPasswordException.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()
        ));
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Object> handleAddressNotFoundException(AddressNotFoundException addressNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                Collections.singletonList(addressNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<Object> handleBrandAlreadyExistsException(BrandAlreadyExistsException brandAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(brandAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(BrandAssociatedWithProductsException.class)
    public ResponseEntity<Object> handleBrandAssociatedWithProductsException(BrandAssociatedWithProductsException brandAssociatedWithProductsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(brandAssociatedWithProductsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Object> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException categoryAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                Collections.singletonList(categoryAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }
}
