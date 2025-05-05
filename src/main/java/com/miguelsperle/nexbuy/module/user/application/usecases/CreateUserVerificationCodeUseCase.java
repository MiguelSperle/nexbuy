package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserVerificationCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreateUserVerificationCodeUseCase implements ICreateUserVerificationCodeUseCase {
    private final IUserVerificationCodeGateway userVerificationCodeGateway;
    private final ICodeProvider codeProvider;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;

    private final static String NUMERIC_CHARACTERS = "0123456789";
    private final static int CODE_LENGTH = 6;

    @Override
    public void execute(CreateUserVerificationCodeUseCaseInput createUserVerificationCodeUseCaseInput) {
        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, NUMERIC_CHARACTERS);

        final User user = createUserVerificationCodeUseCaseInput.getUser();

        final UserVerificationCode newUserVerificationCode = UserVerificationCode.newUserVerificationCode(codeGenerated, user, LocalDateTime.now().plusMinutes(15));

        final UserVerificationCode savedUserVerificationCode = this.userVerificationCodeGateway.save(newUserVerificationCode);

        this.domainEventPublisherProvider.publish(new UserVerificationCodeCreatedEvent(
                savedUserVerificationCode.getUser().getEmail(),
                savedUserVerificationCode.getCode()
        ));
    }
}