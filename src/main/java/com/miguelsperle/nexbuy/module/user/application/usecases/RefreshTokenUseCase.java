package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.IJwtService;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.RefreshTokenExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.RefreshTokenNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IRefreshTokenUseCase;
import com.miguelsperle.nexbuy.core.application.utils.ExpirationUtils;
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

        if (ExpirationUtils.isExpired(refreshToken.getExpiresIn(), LocalDateTime.now())) {
            this.refreshTokenGateway.deleteById(refreshToken.getId());
            throw new RefreshTokenExpiredException("Refresh token has expired");
        }

        final String jwtTokenGenerated = this.jwtService.generateJwt(refreshToken.getUser().getId());

        return new RefreshTokenUseCaseOutput(jwtTokenGenerated);
    }

    private RefreshToken getRefreshTokenByToken(String token) {
        return this.refreshTokenGateway.findByToken(token)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found"));
    }
}
