package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.jwt.IJwtService;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.RefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IRefreshTokenRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefreshTokenUseCasesConfiguration {
    @Bean
    public ICreateRefreshTokenUseCase createRefreshTokenUseCase(
            IRefreshTokenRepository refreshTokenRepository
    ) {
        return new CreateRefreshTokenUseCase(refreshTokenRepository);
    }

    @Bean
    public IRefreshTokenUseCase refreshTokenUseCase(
            IRefreshTokenRepository refreshTokenRepository,
            IJwtService jwtService,
            IUserRepository userRepository
    ) {
        return new RefreshTokenUseCase(
                refreshTokenRepository,
                jwtService,
                userRepository
        );
    }
}
