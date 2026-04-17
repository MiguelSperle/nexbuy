package com.miguelsperle.nexbuy.module.user.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.services.JwtGeneratorService;
import com.miguelsperle.nexbuy.module.user.application.usecases.RefreshTokenUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.RefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.RefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefreshTokenUseCasesConfiguration {
    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(
            RefreshTokenRepository refreshTokenRepository,
            JwtGeneratorService jwtGeneratorService,
            UserRepository userRepository
    ) {
        return new RefreshTokenUseCaseImpl(
                refreshTokenRepository,
                jwtGeneratorService,
                userRepository
        );
    }
}
