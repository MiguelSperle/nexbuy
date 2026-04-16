package com.miguelsperle.nexbuy.module.user.infrastructure.configurations.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.abstractions.providers.CodeProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreatePasswordResetCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.ResendVerificationCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.ValidatePasswordResetCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.ResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.usecases.ValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.abstractions.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodeUseCasesConfiguration {
    @Bean
    public ResendVerificationCodeUseCase resendVerificationCodeUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userGateway,
            MessageProducer messageProducer,
            CodeProvider codeProvider
    ) {
        return new ResendVerificationCodeUseCaseImpl(
                userCodeRepository,
                userGateway,
                messageProducer,
                codeProvider
        );
    }

    @Bean
    public CreatePasswordResetCodeUseCase createPasswordResetCodeUseCase(
            UserCodeRepository userCodeRepository,
            UserRepository userRepository,
            CodeProvider codeProvider,
            MessageProducer messageProducer
    ) {
        return new CreatePasswordResetCodeUseCaseImpl(
                userCodeRepository,
                userRepository,
                codeProvider,
                messageProducer
        );
    }

    @Bean
    public ValidatePasswordResetCodeUseCase validatePasswordResetCodeUseCase(
            UserCodeRepository userCodeRepository
    ) {
        return new ValidatePasswordResetCodeUseCaseImpl(userCodeRepository);
    }
}
