package com.miguelsperle.nexbuy.module.user.infrastructure.configuration.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.application.ports.out.providers.CodeProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreatePasswordResetCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateVerificationCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.ResendVerificationCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.usecases.ValidatePasswordResetCodeUseCaseImpl;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.CreateVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.ResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.in.usecases.ValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCodeUseCasesConfiguration {
    @Bean
    public CreateVerificationCodeUseCase createVerificationCodeUseCase(
            UserCodeRepository userCodeRepository,
            CodeProvider codeProvider
    ) {
        return new CreateVerificationCodeUseCaseImpl(
                userCodeRepository,
                codeProvider
        );
    }

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
