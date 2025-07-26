package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.security.ISecurityContextService;
import com.miguelsperle.nexbuy.core.application.ports.out.jwt.IJwtService;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.ports.in.*;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IAuthenticateUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.ILegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.INaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public ICreateUserUseCase createUserUseCase(
            IUserRepository userRepository,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ICreateLegalPersonUseCase createLegalPersonUseCase,
            ICreateNaturalPersonUseCase createNaturalPersonUseCase,
            ICreateVerificationCodeUseCase createUserVerificationCodeUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateUserUseCase(
                userRepository,
                passwordEncryptorProvider,
                createLegalPersonUseCase,
                createNaturalPersonUseCase,
                createUserVerificationCodeUseCase,
                transactionExecutor
        );
    }

    @Bean
    public IAuthenticateUseCase authenticateUseCase(
            IUserRepository userRepository,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IJwtService jwtService,
            ICreateRefreshTokenUseCase createRefreshTokenUseCase
    ) {
        return new com.miguelsperle.nexbuy.module.user.application.usecases.AuthenticateUseCase(
                userRepository,
                passwordEncryptorProvider,
                jwtService,
                createRefreshTokenUseCase
        );
    }

    @Bean
    public IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            IUserRepository userRepository,
            IUserCodeRepository userCodeRepository,
            ITransactionExecutor transactionExecutor
    ) {
        return new UpdateUserToVerifiedUseCase(
                userRepository,
                userCodeRepository,
                transactionExecutor
        );
    }

    @Bean
    public IResetUserPasswordUseCase resetUserPasswordUseCase(
            IUserCodeRepository userCodeRepository,
            IUserRepository userRepository,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ITransactionExecutor transactionExecutor
    ) {
        return new ResetUserPasswordUseCase(
                userCodeRepository,
                userRepository,
                passwordEncryptorProvider,
                transactionExecutor
        );
    }

    @Bean
    public IUpdateUserUseCase updateUserUseCase(
            ISecurityContextService securityContextService,
            IUserRepository userRepository
    ) {
        return new UpdateUserUseCase(securityContextService, userRepository);
    }

    @Bean
    public IGetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            ISecurityContextService securityContextService,
            INaturalPersonRepository naturalPersonGateway,
            ILegalPersonRepository legalPersonGateway,
            IUserRepository userRepository
    ) {
        return new GetAuthenticatedUserUseCase(
                securityContextService,
                naturalPersonGateway,
                legalPersonGateway,
                userRepository
        );
    }

    @Bean
    public IUpdateUserPasswordUseCase updateUserPasswordUseCase(
            ISecurityContextService securityContextService,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IUserRepository userRepository
    ) {
        return new UpdateUserPasswordUseCase(
                securityContextService,
                passwordEncryptorProvider,
                userRepository
        );
    }
}
