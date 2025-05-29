package com.miguelsperle.nexbuy.module.user.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateUserPasswordResetCodeResponse {
    private boolean codeIsValid;
    private String successType;
    private int statusCode;
}
