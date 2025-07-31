package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.providers.DomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreatePasswordResetCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateVerificationCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.ResendVerificationCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.ValidatePasswordResetCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodeUseCasesConfiguration {
    @Bean
    public CreateVerificationCodeUseCase createVerificationCodeUseCase(
            UserCodeRepository userCodeRepository,
            CodeProvider codeProvider,
            DomainEventPublisherProvider domainEventPublisherProvider,
            TransactionExecutor transactionExecutor
    ) {
        return new CreateVerificationCodeUseCaseImpl(
                userCodeRepository,
                codeProvider,
                domainEventPublisherProvider,
                transactionExecutor
        );
    }

    @Bean
    public ResendVerificationCodeUseCase resendVerificationCodeUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userGateway,
            DomainEventPublisherProvider domainEventPublisherProvider,
            CodeProvider codeProvider
    ) {
        return new ResendVerificationCodeUseCaseImpl(
                userCodeRepository,
                userGateway,
                domainEventPublisherProvider,
                codeProvider
        );
    }

    @Bean
    public CreatePasswordResetCodeUseCase createPasswordResetCodeUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            CodeProvider codeProvider,
            DomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new CreatePasswordResetCodeUseCaseImpl(
                userCodeRepository,
                userRepository,
                codeProvider,
                domainEventPublisherProvider
        );
    }

    @Bean
    public ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase(
            UserCodeRepository userCodeRepository
    ) {
        return new ValidatePasswordResetCodeUseCaseImpl(userCodeRepository);
    }
}
