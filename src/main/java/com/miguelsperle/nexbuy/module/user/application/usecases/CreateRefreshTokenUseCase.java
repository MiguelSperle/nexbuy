package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.CreateRefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IRefreshTokenGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.RefreshToken;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

import java.util.Optional;

public class CreateRefreshTokenUseCase implements ICreateRefreshTokenUseCase {
    private final IRefreshTokenGateway refreshTokenGateway;

    public CreateRefreshTokenUseCase(IRefreshTokenGateway refreshTokenGateway) {
        this.refreshTokenGateway = refreshTokenGateway;
    }

    @Override
    public CreateRefreshTokenUseCaseOutput execute(CreateRefreshTokenUseCaseInput createRefreshTokenUseCaseInput) {
        final User user = createRefreshTokenUseCaseInput.user();

        this.getPreviousRefreshTokenByUserId(user.getId()).ifPresent(refreshToken ->
                this.refreshTokenGateway.deleteById(refreshToken.getId())
        );

        final RefreshToken newRefreshToken = RefreshToken.newRefreshToken(user);

        final RefreshToken savedRefreshToken = this.refreshTokenGateway.save(newRefreshToken);

        return new CreateRefreshTokenUseCaseOutput(savedRefreshToken);
    }

    private Optional<RefreshToken> getPreviousRefreshTokenByUserId(String userId) {
        return this.refreshTokenGateway.findByUserId(userId);
    }
}
