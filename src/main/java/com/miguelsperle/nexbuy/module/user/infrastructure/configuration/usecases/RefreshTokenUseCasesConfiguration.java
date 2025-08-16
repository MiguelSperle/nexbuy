package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.services.JwtService;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateRefreshTokenUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.RefreshTokenUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.RefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.RefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefreshTokenUseCasesConfiguration {
    @Bean
    public CreateRefreshTokenUseCase createRefreshTokenUseCase(
            RefreshTokenRepository refreshTokenRepository
    ) {
        return new CreateRefreshTokenUseCaseImpl(refreshTokenRepository);
    }

    @Bean
    public RefreshTokenUseCase refreshTokenUseCase(
            RefreshTokenRepository refreshTokenRepository,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        return new RefreshTokenUseCaseImpl(
                refreshTokenRepository,
                jwtService,
                userRepository
        );
    }
}
