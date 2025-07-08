package com.miguelsperle.nexbuy.core.infrastructure.dtos;

import java.util.List;

public record ErrorMessageResponse(
        List<String> errors,
        String errorType
) {
}
