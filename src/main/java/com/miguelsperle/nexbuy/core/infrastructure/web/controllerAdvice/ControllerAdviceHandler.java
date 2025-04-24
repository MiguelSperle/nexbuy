package com.miguelsperle.nexbuy.core.infrastructure.web.controllerAdvice;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.core.application.exceptions.MissingRequiredComplementException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.JuridicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.PhysicalUserAlreadyExistsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

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
                List.of(missingRequiredComplementException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()
        ));
    }
}
