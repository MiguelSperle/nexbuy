package com.miguelsperle.nexbuy.module.user.infrastructure.configuration;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IAuthenticatedUserService;
import com.miguelsperle.nexbuy.core.domain.abstractions.security.IJwtService;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.*;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.*;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCasesConfiguration {
    @Bean
    public ICreateUserUseCase createUserUseCase(
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ICreateLegalPersonUseCase createLegalPersonUseCase,
            ICreateNaturalPersonUseCase createNaturalPersonUseCase,
            ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateUserUseCase(
                userGateway,
                passwordEncryptorProvider,
                createLegalPersonUseCase,
                createNaturalPersonUseCase,
                createUserVerificationCodeUseCase,
                transactionExecutor
        );
    }

    @Bean
    public ICreateNaturalPersonUseCase createNaturalPersonUseCase(
            INaturalPersonGateway naturalPersonGateway
    ) {
        return new CreateNaturalPersonUseCase(naturalPersonGateway);
    }

    @Bean
    public ICreateLegalPersonUseCase createLegalPersonUseCase(
            ILegalPersonGateway legalPersonGateway
    ) {
        return new CreateLegalPersonUseCase(legalPersonGateway);
    }

    @Bean
    public ICreateUserVerificationCodeUseCase createUserVerificationCodeUseCase(
            IUserCodeGateway userCodeGateway,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ITransactionExecutor transactionExecutor
    ) {
        return new CreateUserVerificationCodeUseCase(
                userCodeGateway,
                codeProvider,
                domainEventPublisherProvider,
                transactionExecutor
        );
    }

    @Bean
    public IAuthenticateUseCase authenticateUseCase(
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IJwtService jwtService,
            ICreateRefreshTokenUseCase createRefreshTokenUseCase
    ) {
        return new AuthenticateUseCase(
                userGateway,
                passwordEncryptorProvider,
                jwtService,
                createRefreshTokenUseCase
        );
    }

    @Bean
    public IResendUserVerificationCodeUseCase resendUserVerificationCodeUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ICodeProvider codeProvider
    ) {
        return new ResendUserVerificationCodeUseCase(
                userCodeGateway,
                userGateway,
                domainEventPublisherProvider,
                codeProvider
        );
    }

    @Bean
    public IUpdateUserToVerifiedUseCase updateUserToVerifiedUseCase(
            IUserGateway userGateway,
            IUserCodeGateway userCodeGateway,
            ITransactionExecutor transactionExecutor
    ) {
        return new UpdateUserToVerifiedUseCase(
                userGateway,
                userCodeGateway,
                transactionExecutor
        );
    }

    @Bean
    public ICreateRefreshTokenUseCase createRefreshTokenUseCase(
            IRefreshTokenGateway refreshTokenGateway
    ) {
        return new CreateRefreshTokenUseCase(refreshTokenGateway);
    }

    @Bean
    public IRefreshTokenUseCase refreshTokenUseCase(
            IRefreshTokenGateway refreshTokenGateway,
            IJwtService jwtService
    ) {
        return new RefreshTokenUseCase(refreshTokenGateway, jwtService);
    }

    @Bean
    public ICreateUserPasswordResetCodeUseCase createUserPasswordResetCodeUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            ICodeProvider codeProvider,
            IDomainEventPublisherProvider domainEventPublisherProvider
    ) {
        return new CreateUserPasswordResetCodeUseCase(
                userCodeGateway,
                userGateway,
                codeProvider,
                domainEventPublisherProvider
        );
    }

    @Bean
    public IValidateUserPasswordResetCodeUseCase validateUserPasswordResetCodeUseCase(
            IUserCodeGateway userCodeGateway
    ) {
        return new ValidateUserPasswordResetCodeUseCase(userCodeGateway);
    }

    @Bean
    public IResetUserPasswordUseCase resetUserPasswordUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ITransactionExecutor transactionExecutor
    ) {
        return new ResetUserPasswordUseCase(
                userCodeGateway,
                userGateway,
                passwordEncryptorProvider,
                transactionExecutor
        );
    }

    @Bean
    public IUpdateUserInformationUseCase updateUserInformationUseCase(
            IAuthenticatedUserService authenticatedUserService, IUserGateway userGateway
    ) {
        return new UpdateUserInformationUseCase(authenticatedUserService, userGateway);
    }

    @Bean
    public IGetAuthenticatedUserUseCase getAuthenticatedUserUseCase(
            IAuthenticatedUserService authenticatedUserService, INaturalPersonGateway naturalPersonGateway,
            ILegalPersonGateway legalPersonGateway
    ) {
        return new GetAuthenticatedUserUseCase(authenticatedUserService, naturalPersonGateway, legalPersonGateway);
    }

    @Bean
    public IUpdateUserPasswordUseCase updateUserPasswordUseCase(
            IAuthenticatedUserService authenticatedUserService,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            IUserGateway userGateway
    ) {
        return new UpdateUserPasswordUseCase(
                authenticatedUserService,
                passwordEncryptorProvider,
                userGateway
        );
    }

    @Bean
    public ICreateAddressUseCase createAddressUseCase(
            IAddressGateway addressGateway, IAuthenticatedUserService authenticatedUserService
    ) {
        return new CreateAddressUseCase(addressGateway, authenticatedUserService);
    }

    @Bean
    public IUpdateAddressUseCase updateAddressUseCase(
            IAddressGateway addressGateway
    ) {
        return new UpdateAddressUseCase(addressGateway);
    }

    @Bean
    public IGetAddressesUseCase getAddressesUseCase(
            IAddressGateway addressGateway, IAuthenticatedUserService authenticatedUserService
    ) {
        return new GetAddressesUseCase(addressGateway, authenticatedUserService);
    }

    @Bean
    public IGetAddressUseCase getAddressUseCase(IAddressGateway addressGateway) {
        return new GetAddressUseCase(addressGateway);
    }

    @Bean
    public IDeleteAddressUseCase deleteAddressUseCase(IAddressGateway addressGateway) {
        return new DeleteAddressUseCase(addressGateway);
    }
}
