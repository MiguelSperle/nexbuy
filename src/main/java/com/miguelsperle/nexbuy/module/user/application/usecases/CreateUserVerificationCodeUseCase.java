package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeGeneratorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserVerificationCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreateUserVerificationCodeUseCase implements ICreateUserVerificationCodeUseCase {
    private final IUserVerificationCodeGateway userVerificationCodeGateway;
    private final IUserGateway userGateway;
    private final ICodeGeneratorProvider codeGeneratorProvider;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;

    @Override
    public void execute(CreateUserVerificationCodeUseCaseInput createUserVerificationCodeUseCaseInput) {
        final User user = this.getUserById(createUserVerificationCodeUseCaseInput.getUserId());

        final String codeGenerated = this.codeGeneratorProvider.generateCode(6, "0123456789");

        final UserVerificationCode newUserVerificationCode = UserVerificationCode.newUserVerificationCode(codeGenerated, user, LocalDateTime.now().plusMinutes(15));

        final UserVerificationCode savedUserVerificationCode = this.userVerificationCodeGateway.save(newUserVerificationCode);

        this.domainEventPublisherProvider.publish(new UserVerificationCodeCreatedEvent(
                savedUserVerificationCode.getUser().getEmail(),
                savedUserVerificationCode.getCode()
        ));
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}