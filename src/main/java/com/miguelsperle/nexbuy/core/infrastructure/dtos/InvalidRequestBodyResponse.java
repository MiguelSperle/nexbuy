package com.miguelsperle.nexbuy.core.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvalidRequestBodyResponse {
    private Map<String, String> errorMessage;
    private int statusCode;
}