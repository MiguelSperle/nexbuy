package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.jwt.IJwtService;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IAuthenticateUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.AuthenticateUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateRefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.InvalidCredentialsException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotVerifiedException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;

public class AuthenticateUseCase implements IAuthenticateUseCase {
    private final IUserRepository userRepository;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final IJwtService jwtService;
    private final ICreateRefreshTokenUseCase createRefreshTokenUseCase;

    public AuthenticateUseCase(
            IUserRepository userRepository,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IJwtService jwtService,
            ICreateRefreshTokenUseCase createRefreshTokenUseCase
    ) {
        this.userRepository = userRepository;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.jwtService = jwtService;
        this.createRefreshTokenUseCase = createRefreshTokenUseCase;
    }

    @Override
    public AuthenticateUseCaseOutput execute(AuthenticateUseCaseInput authenticateUseCaseInput) {
        final User user = this.getUserByEmail(authenticateUseCaseInput.email());

        if (!this.validatePassword(authenticateUseCaseInput.password(), user.getPassword())) {
            throw InvalidCredentialsException.with("Invalid credentials");
        }

        if (!user.getIsVerified()) {
            throw UserNotVerifiedException.with("User not verified");
        }

        final String jwtTokenGenerated = this.jwtService.generateJwt(user.getId(), user.getAuthorizationRole().name());

        final CreateRefreshTokenUseCaseOutput createRefreshTokenUseCaseOutput = this.createRefreshTokenUseCase.execute(CreateRefreshTokenUseCaseInput.with(
                user.getId()
        ));

        return AuthenticateUseCaseOutput.from(jwtTokenGenerated, createRefreshTokenUseCaseOutput.refreshToken().getToken());
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> InvalidCredentialsException.with("Invalid credentials"));
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptorProvider.matches(password, encodedPassword);
    }
}
