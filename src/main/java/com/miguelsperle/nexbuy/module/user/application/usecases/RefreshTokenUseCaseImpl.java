package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.jwt.JwtService;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.RefreshTokenExpiredException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.RefreshTokenNotFoundException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.RefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.RefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.time.LocalDateTime;

public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RefreshTokenUseCaseImpl(
            RefreshTokenRepository refreshTokenRepository,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshTokenUseCaseOutput execute(RefreshTokenUseCaseInput refreshTokenUseCaseInput) {
        final RefreshToken refreshToken = this.getRefreshTokenByToken(refreshTokenUseCaseInput.refreshToken());

        if (TimeUtils.isExpired(refreshToken.getExpiresIn(), LocalDateTime.now())) {
            this.deleteRefreshTokenById(refreshToken.getId());
            throw RefreshTokenExpiredException.with("Refresh token has expired");
        }

        final User user = this.getUserById(refreshToken.getUserId());

        final String jwtTokenGenerated = this.jwtService.generateJwt(user.getId(), user.getAuthorizationRole().name());

        return RefreshTokenUseCaseOutput.from(jwtTokenGenerated);
    }

    private RefreshToken getRefreshTokenByToken(String token) {
        return this.refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> RefreshTokenNotFoundException.with("Refresh token not found"));
    }

    private void deleteRefreshTokenById(String refreshTokenId) {
        this.refreshTokenRepository.deleteById(refreshTokenId);
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }
}
