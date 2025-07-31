package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateRefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.RefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;

import java.util.Optional;

public class CreateRefreshTokenUseCaseImpl implements CreateRefreshTokenUseCase {
    private final RefreshTokenRepository refreshTokenRepository;

    public CreateRefreshTokenUseCaseImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public CreateRefreshTokenUseCaseOutput execute(CreateRefreshTokenUseCaseInput createRefreshTokenUseCaseInput) {
        final String userId = createRefreshTokenUseCaseInput.userId();

        this.getPreviousRefreshTokenByUserId(userId).ifPresent(refreshToken ->
                this.refreshTokenRepository.deleteById(refreshToken.getId())
        );

        final RefreshToken newRefreshToken = RefreshToken.newRefreshToken(userId);

        final RefreshToken savedRefreshToken = this.saveRefreshToken(newRefreshToken);

        return CreateRefreshTokenUseCaseOutput.from(savedRefreshToken);
    }

    private Optional<RefreshToken> getPreviousRefreshTokenByUserId(String userId) {
        return this.refreshTokenRepository.findByUserId(userId);
    }

    private RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return this.refreshTokenRepository.save(refreshToken);
    }
}
