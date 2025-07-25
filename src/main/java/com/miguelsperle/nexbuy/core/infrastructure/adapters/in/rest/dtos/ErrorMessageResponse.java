package com.miguelsperle.nexbuy.core.infrastructure.adapters.in.rest.dtos;

import java.util.List;

public record ErrorMessageResponse(
        List<String> errors,
        String errorType
) {
    public static ErrorMessageResponse from(List<String> errors, String errorType) {
        return new ErrorMessageResponse(errors, errorType);
    }
}
