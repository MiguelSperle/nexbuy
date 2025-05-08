package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.*;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IJwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    public ICreateUserUseCase createUserUseCase(
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptor,
            ICreateLegalPersonUseCase createJuridicalUserUseCase,
            ICreateNaturalPersonUseCase createPhysicalUserUseCase,
            ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateUserUseCase(
                userGateway,
                passwordEncryptor,
                createJuridicalUserUseCase,
                createPhysicalUserUseCase,
                createUserVerificationCodeUseCase,
                transactionExecutor
        );
    }

    @Bean
    public ICreateNaturalPersonUseCase createNaturalPersonUseCase(INaturalPersonGateway naturalPersonGateway) {
        return new CreateNaturalPersonUseCase(naturalPersonGateway);
    }

    @Bean
    public ICreateLegalPersonUseCase createLegalPersonUseCase(ILegalPersonGateway legalPersonGateway) {
        return new CreateLegalPersonUseCase(legalPersonGateway);
    }

    @Bean
    public ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase(
            IUserVerificationCodeGateway userVerificationCodeGateway, ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new CreateUserVerificationCodeUseCase(userVerificationCodeGateway, codeProvider, domainEventPublisherProvider);
    }

    @Bean
    public IAuthorizationUseCase authorizationUseCase(
            IUserGateway userGateway, IPasswordEncryptorProvider passwordEncryptorProvider,
            IJwtTokenProvider jwtTokenProvider, ICreateRefreshTokenUseCase createRefreshTokenUseCase
    ) {
        return new AuthorizationUseCase(userGateway, passwordEncryptorProvider, jwtTokenProvider, createRefreshTokenUseCase);
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
            IUserGateway userGateway, IUserVerificationCodeGateway userVerificationCodeGateway,
            ITransactionExecutor transactionExecutor
    ) {
        return new UpdateUserToVerifiedUseCase(userGateway, userVerificationCodeGateway, transactionExecutor);
    }

    @Bean
    public ICreateRefreshTokenUseCase createRefreshTokenUseCase(IRefreshTokenGateway refreshTokenGateway) {
        return new CreateRefreshTokenUseCase(refreshTokenGateway);
    }
}
