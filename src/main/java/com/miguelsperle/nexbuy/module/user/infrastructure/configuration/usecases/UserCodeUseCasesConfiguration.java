package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.ResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.ValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ICreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodeUseCasesConfiguration {
    @Bean
    public ICreateVerificationCodeUseCase createVerificationCodeUseCase(
            IUserCodeRepository userCodeRepository,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateVerificationCodeUseCase(
                userCodeRepository,
                codeProvider,
                domainEventPublisherProvider,
                transactionExecutor
        );
    }

    @Bean
    public IResendVerificationCodeUseCase resendVerificationCodeUseCase(
            IUserCodeRepository userCodeRepository,
            IUserRepository userGateway,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ICodeProvider codeProvider
    ) {
        return new ResendVerificationCodeUseCase(
                userCodeRepository,
                userGateway,
                domainEventPublisherProvider,
                codeProvider
        );
    }

    @Bean
    public ICreatePasswordResetCodeUseCase createPasswordResetCodeUseCase(
            IUserCodeRepository userCodeRepository,
            IUserRepository userRepository,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new CreatePasswordResetCodeUseCase(
                userCodeRepository,
                userRepository,
                codeProvider,
                domainEventPublisherProvider
        );
    }

    @Bean
    public IValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase(
            IUserCodeRepository userCodeRepository
    ) {
        return new ValidatePasswordResetCodeUseCase(userCodeRepository);
    }
}
