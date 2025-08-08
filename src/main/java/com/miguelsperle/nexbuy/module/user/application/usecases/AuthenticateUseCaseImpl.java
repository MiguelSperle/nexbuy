package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.jwt.JwtService;
import com.miguelsperle.nexbuy.module.user.application.ports.in.AuthenticateUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.AuthenticateUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateRefreshTokenUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class AuthenticateUseCaseImpl implements AuthenticateUseCase {
    private final UserRepository userRepository;
    private final PasswordEncryptorProvider passwordEncryptorProvider;
    private final JwtService jwtService;
    private final CreateRefreshTokenUseCase createRefreshTokenUseCase;

    public AuthenticateUseCaseImpl(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            JwtService jwtService,
            CreateRefreshTokenUseCase createRefreshTokenUseCase
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
            throw DomainException.with("Wrong credentials", 401);
        }

        if (user.getUserStatus() == UserStatus.UNVERIFIED) {
            throw DomainException.with("User not verified", 403);
        }

        if (user.getUserStatus() == UserStatus.DELETED) {
            throw DomainException.with("User has been deleted and cannot authenticate", 403);
        }

        final String jwtTokenGenerated = this.jwtService.generateJwt(user.getId(), user.getAuthorizationRole().name());

        final CreateRefreshTokenUseCaseOutput createRefreshTokenUseCaseOutput = this.createRefreshTokenUseCase.execute(CreateRefreshTokenUseCaseInput.with(
                user.getId()
        ));

        return AuthenticateUseCaseOutput.from(jwtTokenGenerated, createRefreshTokenUseCaseOutput.refreshToken().getToken());
    }

    private User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> DomainException.with("Wrong credentials", 401));
    }

    private boolean validatePassword(String password, String encodedPassword) {
        return this.passwordEncryptorProvider.matches(password, encodedPassword);
    }
}
