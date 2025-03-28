package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.mediator.IRequestHandler;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserHandlerInput;
import com.miguelsperle.nexbuy.module.user.application.handlers.CreateUserHandler;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlersConfiguration {
    @Bean
    public IRequestHandler<CreateUserHandlerInput, Void> createUserHandler(
            ICreateUserUseCase createUserUseCase, ICreatePhysicalUserUseCase createPhysicalUserUseCase,
            ICreateJuridicalUserUseCase createJuridicalUserUseCase, ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase
    ) {
        return new CreateUserHandler(createUserUseCase, createPhysicalUserUseCase, createJuridicalUserUseCase, createUserVerificationCodeUseCase);
    }
}
