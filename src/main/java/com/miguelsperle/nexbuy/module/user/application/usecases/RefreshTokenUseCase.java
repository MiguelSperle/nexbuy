package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.jwt.IJwtService;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.RefreshTokenExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.RefreshTokenNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IRefreshTokenUseCase;
import com.miguelsperle.nexbuy.core.application.utils.ExpirationUtils;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IRefreshTokenGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.time.LocalDateTime;

public class RefreshTokenUseCase implements IRefreshTokenUseCase {
    private final IRefreshTokenGateway refreshTokenGateway;
    private final IJwtService jwtService;
    private final IUserGateway userGateway;

    public RefreshTokenUseCase(
            IRefreshTokenGateway refreshTokenGateway,
            IJwtService jwtService,
            IUserGateway userGateway
    ) {
        this.refreshTokenGateway = refreshTokenGateway;
        this.jwtService = jwtService;
        this.userGateway = userGateway;
    }

    @Override
    public RefreshTokenUseCaseOutput execute(RefreshTokenUseCaseInput refreshTokenUseCaseInput) {
        final RefreshToken refreshToken = this.getRefreshTokenByToken(refreshTokenUseCaseInput.refreshToken());

        if (ExpirationUtils.isExpired(refreshToken.getExpiresIn(), LocalDateTime.now())) {
            this.deleteRefreshTokenById(refreshToken.getId());
            throw RefreshTokenExpiredException.with("Refresh token has expired");
        }

        final User user = this.getUserById(refreshToken.getUserId());

        final String jwtTokenGenerated = this.jwtService.generateJwt(user.getId(), user.getAuthorizationRole().name());

        return RefreshTokenUseCaseOutput.from(jwtTokenGenerated);
    }

    private RefreshToken getRefreshTokenByToken(String token) {
        return this.refreshTokenGateway.findByToken(token)
                .orElseThrow(() -> RefreshTokenNotFoundException.with("Refresh token not found"));
    }

    private void deleteRefreshTokenById(String refreshTokenId) {
        this.refreshTokenGateway.deleteById(refreshTokenId);
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }
}
