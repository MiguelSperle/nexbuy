package com.miguelsperle.nexbuy.module.product.infrastructure.configuration.exceptionHandler;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.module.product.domain.exceptions.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProductExceptionHandler {
    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleBrandAlreadyExistsException(BrandAlreadyExistsException brandAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(brandAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleBrandNotFoundException(BrandNotFoundException brandNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(brandNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(BrandAssociatedWithProductsException.class)
    public ResponseEntity<ErrorMessageResponse> handleBrandAssociatedWithProductsException(BrandAssociatedWithProductsException brandAssociatedWithProductsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(brandAssociatedWithProductsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException categoryAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(categoryAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(categoryNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(CategoryAssociatedWithProductsException.class)
    public ResponseEntity<ErrorMessageResponse> handleCategoryAssociatedWithProductsException(CategoryAssociatedWithProductsException categoryAssociatedWithProductsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(categoryAssociatedWithProductsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(ColorAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> handleColorAlreadyExistsException(ColorAlreadyExistsException colorAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(colorAlreadyExistsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(ColorNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleColorNotFoundException(ColorNotFoundException colorNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(colorNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(ColorAssociatedWithProductsException.class)
    public ResponseEntity<ErrorMessageResponse> handleColorAssociatedWithProductsException(ColorAssociatedWithProductsException colorAssociatedWithProductsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(colorAssociatedWithProductsException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(productNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }

    @ExceptionHandler(ProductStatusNotAllowedException.class)
    public ResponseEntity<ErrorMessageResponse> handleProductStatusNotAllowedException(ProductStatusNotAllowedException productStatusNotAllowedException) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ErrorMessageResponse.from(
                Collections.singletonList(productStatusNotAllowedException.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()
        ));
    }

    @ExceptionHandler(ProductAlreadyDeletedException.class)
    public ResponseEntity<ErrorMessageResponse> handleProductAlreadyDeletedException(ProductAlreadyDeletedException productAlreadyDeletedException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorMessageResponse.from(
                Collections.singletonList(productAlreadyDeletedException.getMessage()), HttpStatus.CONFLICT.getReasonPhrase()
        ));
    }
}
