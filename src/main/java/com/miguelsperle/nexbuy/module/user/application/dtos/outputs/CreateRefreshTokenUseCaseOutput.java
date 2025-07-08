package com.miguelsperle.nexbuy.module.user.application.dtos.outputs;

import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;

public record CreateRefreshTokenUseCaseOutput(
        RefreshToken refreshToken
) {
}

