package com.miguelsperle.nexbuy.module.user.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenUseCaseOutput {
    private String accessToken;
}
