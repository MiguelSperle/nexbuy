package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IJwtService;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    public ICreateUserUseCase createUserUseCase(
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ICreateLegalPersonUseCase createLegalPersonUseCase,
            ICreateNaturalPersonUseCase createNaturalPersonUseCase,
            ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateUserUseCase(
                userGateway,
                passwordEncryptorProvider,
                createLegalPersonUseCase,
                createNaturalPersonUseCase,
                createUserVerificationCodeUseCase,
                transactionExecutor
        );
    }

    @Bean
    public ICreateNaturalPersonUseCase createNaturalPersonUseCase(
            INaturalPersonGateway naturalPersonGateway
    ) {
        return new CreateNaturalPersonUseCase(naturalPersonGateway);
    }

    @Bean
    public ICreateLegalPersonUseCase createLegalPersonUseCase(
            ILegalPersonGateway legalPersonGateway
    ) {
        return new CreateLegalPersonUseCase(legalPersonGateway);
    }

    @Bean
    public ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase(
            IUserVerificationCodeGateway userVerificationCodeGateway,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateUserVerificationCodeUseCase(
                userVerificationCodeGateway,
                codeProvider,
                domainEventPublisherProvider,
                transactionExecutor
        );
    }

    @Bean
    public IAuthenticateUseCase authenticateUseCase(
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IJwtService jwtService,
            ICreateRefreshTokenUseCase createRefreshTokenUseCase
    ) {
        return new AuthenticateUseCase(
                userGateway,
                passwordEncryptorProvider,
                jwtService,
                createRefreshTokenUseCase
        );
    }

    @Bean
    public IResendUserVerificationCodeUseCase resendUserVerificationCodeUseCase(
            IUserVerificationCodeGateway userVerificationCodeGateway,
            IUserGateway userGateway,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ICodeProvider codeProvider
    ) {
        return new ResendUserVerificationCodeUseCase(
                userVerificationCodeGateway,
                userGateway,
                domainEventPublisherProvider,
                codeProvider
        );
    }

    @Bean
    public IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            IUserGateway userGateway,
            IUserVerificationCodeGateway userVerificationCodeGateway,
            ITransactionExecutor transactionExecutor
    ) {
        return new UpdateUserToVerifiedUseCase(
                userGateway,
                userVerificationCodeGateway,
                transactionExecutor
        );
    }

    @Bean
    public ICreateRefreshTokenUseCase createRefreshTokenUseCase(
            IRefreshTokenGateway refreshTokenGateway
    ) {
        return new CreateRefreshTokenUseCase(refreshTokenGateway);
    }

    @Bean
    public IRefreshTokenUseCase refreshTokenUseCase(
            IRefreshTokenGateway refreshTokenGateway,
            IJwtService jwtService
    ) {
        return new RefreshTokenUseCase(refreshTokenGateway, jwtService);
    }
}
