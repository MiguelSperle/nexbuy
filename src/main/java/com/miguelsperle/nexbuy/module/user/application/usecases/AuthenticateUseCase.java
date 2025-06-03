package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IJwtService;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.AuthenticateUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.CreateRefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.InvalidCredentialsException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotVerifiedException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IAuthenticateUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticateUseCase implements IAuthenticateUseCase {
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final IJwtService jwtService;
    private final ICreateRefreshTokenUseCase createRefreshTokenUseCase;

    @Override
    public AuthenticateUseCaseOutput execute(AuthenticateUseCaseInput authenticateUseCaseInput) {
        final User user = this.getUserByEmail(authenticateUseCaseInput.getEmail());

        if (!this.validatePassword(authenticateUseCaseInput.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        if (!user.getIsVerified()) {
            throw new UserNotVerifiedException("User not verified");
        }

        final String jwtTokenGenerated = this.jwtService.generateJwt(user.getId());

        final CreateRefreshTokenUseCaseOutput createRefreshTokenUseCaseOutput = this.createRefreshTokenUseCase.execute(new CreateRefreshTokenUseCaseInput(
                user
        ));

        return new AuthenticateUseCaseOutput(jwtTokenGenerated, createRefreshTokenUseCaseOutput.getRefreshToken());
    }

    private User getUserByEmail(String email) {
        return this.userGateway.findByEmail(email).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptorProvider.matches(password, encodedPassword);
    }
}
