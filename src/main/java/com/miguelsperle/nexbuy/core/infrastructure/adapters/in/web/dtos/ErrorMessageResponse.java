package com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos;

import java.util.List;

public record ErrorMessageResponse(
        List<String> errors,
        String errorType
) {
    public static ErrorMessageResponse from(List<String> errors, String errorType) {
        return new ErrorMessageResponse(errors, errorType);
    }
}
