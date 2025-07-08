package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

public record AuthenticateUseCaseOutput(
        String accessToken,
        String refreshToken
) {
}

