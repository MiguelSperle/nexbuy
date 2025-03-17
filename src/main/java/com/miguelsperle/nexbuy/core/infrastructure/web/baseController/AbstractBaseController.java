package com.miguelsperle.nexbuy.core.infrastructure.web.baseController;

import com.miguelsperle.nexbuy.core.infrastructure.exceptions.InvalidRequestBodyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBaseController {
    public void validateRequestBody(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final Map<String, String> fieldErrors = new HashMap<>();

            bindingResult.getAllErrors().forEach(objectError -> {
                final String fieldName = ((FieldError) objectError).getField();
                final String errorMessage = objectError.getDefaultMessage();

                fieldErrors.put(fieldName, errorMessage);
            });

            throw new InvalidRequestBodyException(fieldErrors);
        }
    }
}
