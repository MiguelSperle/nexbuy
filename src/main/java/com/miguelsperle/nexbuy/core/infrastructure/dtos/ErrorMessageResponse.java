package com.miguelsperle.nexbuy.core.infrastructure.dtos;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {
    private String errorMessage;
    private int statusCode;
}
