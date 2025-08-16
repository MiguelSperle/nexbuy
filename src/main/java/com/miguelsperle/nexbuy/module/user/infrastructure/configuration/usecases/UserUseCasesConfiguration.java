package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.*;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.PasswordEncryptorProvider;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.SecurityContextService;
import com.miguelsperle.nexbuy.shared.application.ports.out.services.JwtService;
import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.LegalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.NaturalPersonRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public CreateUserUseCase createUserUseCase(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            CreateLegalPersonUseCase createLegalPersonUseCase,
            CreateNaturalPersonUseCase createNaturalPersonUseCase,
            TransactionExecutor transactionExecutor,
            MessageProducer messageProducer
    ) {
        return new CreateUserUseCaseImpl(
                userRepository,
                passwordEncryptorProvider,
                createLegalPersonUseCase,
                createNaturalPersonUseCase,
                transactionExecutor,
                messageProducer
        );
    }

    @Bean
    public AuthenticateUseCase authenticateUseCase(
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            JwtService jwtService,
            CreateRefreshTokenUseCase createRefreshTokenUseCase
    ) {
        return new AuthenticateUseCaseImpl(
                userRepository,
                passwordEncryptorProvider,
                jwtService,
                createRefreshTokenUseCase
        );
    }

    @Bean
    public UpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            TransactionExecutor transactionExecutor
    ) {
        return new UpdateUserToVerifiedUseCaseImpl(
                userRepository,
                userCodeRepository,
                transactionExecutor
        );
    }

    @Bean
    public ResetUserPasswordUseCase resetUserPasswordUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            PasswordEncryptorProvider passwordEncryptorProvider,
            TransactionExecutor transactionExecutor
    ) {
        return new ResetUserPasswordUseCaseImpl(
                userCodeRepository,
                userRepository,
                passwordEncryptorProvider,
                transactionExecutor
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
