package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.ResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.ValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodeUseCasesConfiguration {
    @Bean
    public ICreateVerificationCodeUseCase createVerificationCodeUseCase(
            IUserCodeGateway userCodeGateway,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateVerificationCodeUseCase(
                userCodeGateway,
                codeProvider,
                domainEventPublisherProvider,
                transactionExecutor
        );
    }

    @Bean
    public IResendVerificationCodeUseCase resendVerificationCodeUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ICodeProvider codeProvider
    ) {
        return new ResendVerificationCodeUseCase(
                userCodeGateway,
                userGateway,
                domainEventPublisherProvider,
                codeProvider
        );
    }

    @Bean
    public ICreatePasswordResetCodeUseCase createPasswordResetCodeUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new CreatePasswordResetCodeUseCase(
                userCodeGateway,
                userGateway,
                codeProvider,
                domainEventPublisherProvider
        );
    }

    @Bean
    public IValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase(
            IUserCodeGateway userCodeGateway
    ) {
        return new ValidatePasswordResetCodeUseCase(userCodeGateway);
    }
}
