package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeGeneratorProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.ICreateUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserVerificationCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateUserVerificationCodeUseCase implements ICreateUserVerificationCodeUseCase {
    private final IUserVerificationCodeGateway userVerificationCodeGateway;
    private final ICodeGeneratorProvider codeGeneratorProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void execute(CreateUserVerificationCodeUseCaseInput createUserVerificationCodeUseCaseInput) {
        final String codeGenerated = this.codeGeneratorProvider.generateCode(6, "0123456789");

        final UserVerificationCode newUserVerificationCode = UserVerificationCode.newUserVerificationCode(codeGenerated, createUserVerificationCodeUseCaseInput.getUser(), LocalDateTime.now().plusMinutes(15));

        final UserVerificationCode savedUserVerificationCode = this.userVerificationCodeGateway.save(newUserVerificationCode);

        this.applicationEventPublisher.publishEvent(new UserVerificationCodeCreatedEvent(this, savedUserVerificationCode));
    }
}



