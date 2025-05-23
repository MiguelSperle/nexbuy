package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IJwtService;
import com.miguelsperle.nexbuy.module.user.application.dtos.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.RefreshTokenExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.RefreshTokenNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IRefreshTokenGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class RefreshTokenUseCase implements IRefreshTokenUseCase {
    private final IRefreshTokenGateway refreshTokenGateway;
    private final IJwtService jwtService;

    @Override
    public RefreshTokenUseCaseOutput execute(RefreshTokenUseCaseInput refreshTokenUseCaseInput) {
        final RefreshToken refreshToken = this.getRefreshTokenByToken(refreshTokenUseCaseInput.getRefreshToken());

        if (LocalDateTime.now().isAfter(refreshToken.getExpiresIn())) {
            this.refreshTokenGateway.deleteById(refreshToken.getId());
            throw new RefreshTokenExpiredException("Refresh token expired. Please make a new authentication process");
        }

        final String jwtTokenGenerated = this.jwtService.generateJwt(refreshToken.getUser().getId());

        return new RefreshTokenUseCaseOutput(jwtTokenGenerated);
    }

    private RefreshToken getRefreshTokenByToken(String token) {
        return this.refreshTokenGateway.findByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found by token: " + token));
    }
}
