package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.security.jwt.IJwtService;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.RefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IRefreshTokenUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IRefreshTokenGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefreshTokenUseCasesConfiguration {
    @Bean
    public ICreateRefreshTokenUseCase createRefreshTokenUseCase(
            IRefreshTokenGateway refreshTokenGateway
    ) {
        return new CreateRefreshTokenUseCase(refreshTokenGateway);
    }

    @Bean
    public IRefreshTokenUseCase refreshTokenUseCase(
            IRefreshTokenGateway refreshTokenGateway,
            IJwtService jwtService,
            IUserGateway userGateway
    ) {
        return new RefreshTokenUseCase(
                refreshTokenGateway,
                jwtService,
                userGateway
        );
    }
}
