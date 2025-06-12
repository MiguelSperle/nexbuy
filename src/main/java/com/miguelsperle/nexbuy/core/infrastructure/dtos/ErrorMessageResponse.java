package com.miguelsperle.nexbuy.core.infrastructure.dtos;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {
    private List<String> errors;
    private String errorType;
}
