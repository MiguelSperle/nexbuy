package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeGeneratorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.CreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateJuridicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreatePhysicalUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
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
            IUserGateway userGateway, IPhysicalUserGateway physicalUserGateway,
            IJuridicalUserGateway juridicalUserGateway, IPasswordEncryptorProvider passwordEncryptor
    ) {
        return new CreateUserUseCase(userGateway, physicalUserGateway, juridicalUserGateway, passwordEncryptor);
    }

    @Bean
    public ICreatePhysicalUserUseCase createPhysicalUserUseCase(IPhysicalUserGateway physicalUserGateway) {
        return new CreatePhysicalUserUseCase(physicalUserGateway);
    }

    @Bean
    public ICreateJuridicalUserUseCase createJuridicalUserUseCase(IJuridicalUserGateway juridicalUserGateway) {
        return new CreateJuridicalUserUseCase(juridicalUserGateway);
    }

    @Bean
    public ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase(
            IUserVerificationCodeGateway userVerificationCodeGateway, ICodeGeneratorProvider codeGeneratorProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new CreateUserVerificationCodeUseCase(userVerificationCodeGateway, codeGeneratorProvider, domainEventPublisherProvider);
    }
}
