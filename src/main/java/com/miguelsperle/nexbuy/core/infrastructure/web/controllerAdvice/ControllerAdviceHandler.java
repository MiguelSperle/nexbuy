package com.miguelsperle.nexbuy.core.infrastructure.web.controllerAdvice;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ControllerAdviceHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        final List<String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();

        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                errors, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        final String errorMessage = "Invalid request body";

        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                List.of(errorMessage), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception) {
        log.error("Exception of type {} occurred: {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        final String errorMessage = "Internal server error, we are working on it";

        return ResponseEntity.internalServerError().body(new ErrorMessageResponse(
                List.of(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR.value()
        ));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                List.of(userAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(PhysicalUserAlreadyExistsException.class)
    public ResponseEntity<Object> handlePhysicalUserAlreadyExistsException(PhysicalUserAlreadyExistsException physicalUserAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                List.of(physicalUserAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(JuridicalUserAlreadyExistsException.class)
    public ResponseEntity<Object> handleJuridicalUserAlreadyExistsException(JuridicalUserAlreadyExistsException juridicalUserAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                List.of(juridicalUserAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(MissingRequiredComplementException.class)
    public ResponseEntity<Object> handleMissingRequiredDataException(MissingRequiredComplementException missingRequiredComplementException) {
        return ResponseEntity.badRequest().body(new ErrorMessageResponse(
                List.of(missingRequiredComplementException.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()
        ));
    }

    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<Object> handleUserNotVerifiedException(UserNotVerifiedException userNotVerifiedException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessageResponse(
                List.of(userNotVerifiedException.getMessage()), HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value()
        ));
    }

    @ExceptionHandler(UserAlreadyVerifiedException.class)
    public ResponseEntity<Object> handleUserAlreadyVerifiedException(UserAlreadyVerifiedException userAlreadyVerifiedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessageResponse(
                List.of(userAlreadyVerifiedException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()
        ));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageResponse(
                List.of(invalidCredentialsException.getMessage()), HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value()
        ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                List.of(userNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(UserVerificationCodeNotFoundException.class)
    public ResponseEntity<Object> handleUserVerificationCodeNotFoundException(UserVerificationCodeNotFoundException userVerificationCodeNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageResponse(
                List.of(userVerificationCodeNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.value()
        ));
    }

    @ExceptionHandler(UserVerificationCodeExpiredException.class)
    public ResponseEntity<Object> handleUserVerificationCodeExpiredException(UserVerificationCodeExpiredException userVerificationCodeExpiredException) {
        return ResponseEntity.status(HttpStatus.GONE).body(new ErrorMessageResponse(
                List.of(userVerificationCodeExpiredException.getMessage()), HttpStatus.GONE.getReasonPhrase(), HttpStatus.GONE.value()
        ));
    }
}
