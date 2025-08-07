package com.miguelsperle.nexbuy.module.coupon.infrastructure.configuration.exceptionHandler;

import com.miguelsperle.nexbuy.module.coupon.domain.exceptions.*;
import com.miguelsperle.nexbuy.shared.infrastructure.configuration.dtos.responses.ErrorMessageResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CouponExceptionHandler {
    @ExceptionHandler(CouponAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleCouponAlreadyExistsException(CouponAlreadyExistsException couponAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(couponAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(InvalidCouponConfigurationException.class)
    public ResponseEntity<ErrorMessageResponse> handleInvalidCouponConfigurationException(InvalidCouponConfigurationException invalidCouponConfigurationException) {
        return ResponseEntity.badRequest().body(ErrorMessageResponse.from(
                Collections.singletonList(invalidCouponConfigurationException.getMessage()), HttpStatus.BAD_REQUEST.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleCouponNotFoundException(CouponNotFoundException couponNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(couponNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CouponAlreadyActivatedException.class)
    public ResponseEntity<ErrorMessageResponse> handleCouponAlreadyActivatedException(CouponAlreadyActivatedException couponAlreadyActivatedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(couponAlreadyActivatedException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CouponAlreadyDeactivatedException.class)
    public ResponseEntity<ErrorMessageResponse> handleCouponAlreadyDeactivatedException(CouponAlreadyDeactivatedException couponAlreadyDeactivatedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(couponAlreadyDeactivatedException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }
}
