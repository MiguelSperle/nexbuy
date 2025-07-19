package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;

public record CreateRefreshTokenUseCaseOutput(
        RefreshToken refreshToken
) {
    public static CreateRefreshTokenUseCaseOutput from(RefreshToken refreshToken) {
        return new CreateRefreshTokenUseCaseOutput(refreshToken);
    }
}

