package com.miguelsperle.nexbuy.module.user.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.*;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.*;
import com.miguelsperle.nexbuy.shared.application.abstractions.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.abstractions.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.abstractions.services.JwtService;
import com.miguelsperle.nexbuy.shared.application.abstractions.wrapper.TransactionManager;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public CreateUserUseCase createUserUseCase(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            NaturalPersonRepository naturalPersonRepository,
            LegalPersonRepository legalPersonRepository,
            TransactionManager transactionManager,
            MessageProducer messageProducer
    ) {
        return new CreateUserUseCaseImpl(
                userRepository,
                passwordEncryptorProvider,
                naturalPersonRepository,
                legalPersonRepository,
                transactionManager,
                messageProducer
        );
    }

    @Bean
    public AuthenticateUseCase authenticateUseCase(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            JwtService jwtService,
            RefreshTokenRepository refreshTokenRepository
    ) {
        return new AuthenticateUseCaseImpl(
                userRepository,
                passwordEncryptorProvider,
                jwtService,
                refreshTokenRepository
        );
    }

    @Bean
    public UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            TransactionManager transactionManager
    ) {
        return new UpdateUserToVerifiedUseCaseImpl(
                userRepository,
                userCodeRepository,
                transactionManager
        );
    }

    @Bean
    public ResetUserPasswordUseCase resetUserPasswordUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            TransactionManager transactionManager
    ) {
        return new ResetUserPasswordUseCaseImpl(
                userCodeRepository,
                userRepository,
                passwordEncryptorProvider,
                transactionManager
        );
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            SecurityContextService securityContextService,
            UserRepository userRepository
    ) {
        return new UpdateUserUseCaseImpl(securityContextService, userRepository);
    }

    @Bean
    public GetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            SecurityContextService securityContextService,
            NaturalPersonRepository naturalPersonGateway,
            LegalPersonRepository legalPersonGateway,
            UserRepository userRepository
    ) {
        return new GetAuthenticatedUserUseCaseImpl(
                securityContextService,
                naturalPersonGateway,
                legalPersonGateway,
                userRepository
        );
    }

    @Bean
    public UpdateUserPasswordUseCase updateUserPasswordUseCase(
            SecurityContextService securityContextService,
            PasswordEncryptorProvider passwordEncryptorProvider,
            UserRepository userRepository
    ) {
        return new UpdateUserPasswordUseCaseImpl(
                securityContextService,
                passwordEncryptorProvider,
                userRepository
        );
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(
            UserRepository userRepository,
            SecurityContextService securityContextService
    ) {
        return new DeleteUserUseCaseImpl(userRepository, securityContextService);
    }
}
