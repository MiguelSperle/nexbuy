package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthorizationUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthorizationUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateRefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.InvalidCredentialsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotVerifiedException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IAuthorizationUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IJwtTokenProvider;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizationUseCase implements IAuthorizationUseCase {
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final IJwtTokenProvider jwtTokenProvider;
    private final ICreateRefreshTokenUseCase createRefreshTokenUseCase;

    @Override
    public AuthorizationUseCaseOutput execute(AuthorizationUseCaseInput authorizationUseCaseInput) {
        final User user = this.getUserByEmail(authorizationUseCaseInput.getEmail());

        if (!this.validatePassword(authorizationUseCaseInput.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid authorization credentials");
        }

        if (!user.getIsVerified()) {
            throw new UserNotVerifiedException("User not verified");
        }

        final String jwtTokenGenerated = this.jwtTokenProvider.generateJwt(user);

        final CreateRefreshTokenUseCaseOutput createRefreshTokenUseCaseOutput = this.createRefreshTokenUseCase.execute(new CreateRefreshTokenUseCaseInput(
                user
        ));

        return new AuthorizationUseCaseOutput(jwtTokenGenerated, createRefreshTokenUseCaseOutput.getRefreshToken());
    }

    private User getUserByEmail(String email) {
        return this.userGateway.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Invalid authorization credentials"));
    }

    private boolean validatePassword(String password, String encryptedPassword) {
        return this.passwordEncryptorProvider.matches(password, encryptedPassword);
    }
}
