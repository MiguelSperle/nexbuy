package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse {
    private String accessToken;
    private String refreshToken;
    private String successType;
    private int statusCode;
}
