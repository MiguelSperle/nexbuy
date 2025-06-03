package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

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
