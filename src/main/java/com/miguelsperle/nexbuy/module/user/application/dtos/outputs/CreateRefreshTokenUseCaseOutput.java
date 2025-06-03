package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRefreshTokenUseCaseOutput {
    private String refreshToken;
}
