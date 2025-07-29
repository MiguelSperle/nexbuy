package com.miguelsperle.nexbuy.module.inventory.infrastructure.configuration.exceptionHandler;

import com.miguelsperle.nexbuy.core.infrastructure.configuration.dtos.ErrorMessageResponse;
import com.miguelsperle.nexbuy.module.inventory.domain.exceptions.InventoryNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InventoryExceptionHandler {
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleInventoryNotFoundException(InventoryNotFoundException inventoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageResponse.from(
                Collections.singletonList(inventoryNotFoundException.getMessage()), HttpStatus.NOT_FOUND.getReasonPhrase()
        ));
    }
}
