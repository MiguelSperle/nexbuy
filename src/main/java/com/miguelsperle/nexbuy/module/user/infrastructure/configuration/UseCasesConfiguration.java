package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeGeneratorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IJuridicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IPhysicalUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserVerificationCodeGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfiguration {
    @Bean
    public ICreateUserUseCase createUserUseCase(
            IUserGateway userGateway, IPasswordEncryptorProvider passwordEncryptor,ICreateJuridicalUserUseCase createJuridicalUserUseCase,
            ICreatePhysicalUserUseCase createPhysicalUserUseCase, ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase
    ) {
        return new CreateUserUseCase(userGateway, passwordEncryptor, createJuridicalUserUseCase, createPhysicalUserUseCase, createUserVerificationCodeUseCase);
    }

    @Bean
    public ICreatePhysicalUserUseCase createPhysicalUserUseCase(
            IPhysicalUserGateway physicalUserGateway, IUserGateway userGateway
    ) {
        return new CreatePhysicalUserUseCase(physicalUserGateway, userGateway);
    }

    @Bean
    public ICreateJuridicalUserUseCase createJuridicalUserUseCase(
            IJuridicalUserGateway juridicalUserGateway, IUserGateway userGateway
    ) {
        return new CreateJuridicalUserUseCase(juridicalUserGateway, userGateway);
    }

    @Bean
    public ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase(
            IUserVerificationCodeGateway userVerificationCodeGateway, IUserGateway userGateway,
            ICodeGeneratorProvider codeGeneratorProvider, IDomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new CreateUserVerificationCodeUseCase(userVerificationCodeGateway, userGateway, codeGeneratorProvider, domainEventPublisherProvider);
    }
}
