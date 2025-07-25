package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.ISecurityContextService;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.jwt.IJwtService;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.ILegalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.INaturalPersonGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public ICreateUserUseCase createUserUseCase(
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ICreateLegalPersonUseCase createLegalPersonUseCase,
            ICreateNaturalPersonUseCase createNaturalPersonUseCase,
            ICreateVerificationCodeUseCase createUserVerificationCodeUseCase,
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
    public IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            IUserGateway userGateway,
            IUserCodeGateway userCodeGateway,
            ITransactionExecutor transactionExecutor
    ) {
        return new UpdateUserToVerifiedUseCase(
                userGateway,
                userCodeGateway,
                transactionExecutor
        );
    }

    @Bean
    public IResetUserPasswordUseCase resetUserPasswordUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ITransactionExecutor transactionExecutor
    ) {
        return new ResetUserPasswordUseCase(
                userCodeGateway,
                userGateway,
                passwordEncryptorProvider,
                transactionExecutor
        );
    }

    @Bean
    public IUpdateUserUseCase updateUserUseCase(
            ISecurityContextService securityContextService, IUserGateway userGateway
    ) {
        return new UpdateUserUseCase(securityContextService, userGateway);
    }

    @Bean
    public IGetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            ISecurityContextService securityContextService,
            INaturalPersonGateway naturalPersonGateway,
            ILegalPersonGateway legalPersonGateway,
            IUserGateway userGateway
    ) {
        return new GetAuthenticatedUserUseCase(
                securityContextService,
                naturalPersonGateway,
                legalPersonGateway,
                userGateway
        );
    }

    @Bean
    public IUpdateUserPasswordUseCase updateUserPasswordUseCase(
            ISecurityContextService securityContextService,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IUserGateway userGateway
    ) {
        return new UpdateUserPasswordUseCase(
                securityContextService,
                passwordEncryptorProvider,
                userGateway
        );
    }
}
