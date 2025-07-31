package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.jwt.JwtService;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateRefreshTokenUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.RefreshTokenUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.RefreshTokenUseCase;
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
