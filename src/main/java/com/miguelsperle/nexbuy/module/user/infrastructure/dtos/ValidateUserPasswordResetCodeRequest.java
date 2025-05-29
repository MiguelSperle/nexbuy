package com.miguelsperle.nexbuy.module.user.infrastructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateUserPasswordResetCodeRequest {
    private String code;
}
