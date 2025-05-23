package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.ResendUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyVerifiedException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResendUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserVerificationCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import com.miguelsperle.nexbuy.module.user.domain.events.UserVerificationCodeCreatedEvent;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ResendUserVerificationCodeUseCase implements IResendUserVerificationCodeUseCase {
    private final IUserVerificationCodeGateway userVerificationCodeGateway;
    private final IUserGateway userGateway;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ICodeProvider codeProvider;

    private final static String NUMERIC_CHARACTERS = "0123456789";
    private final static int CODE_LENGTH = 6;

    @Override
    public void execute(ResendUserVerificationCodeUseCaseInput resendUserVerificationCodeUseCaseInput) {
        final User user = this.getUserByEmail(resendUserVerificationCodeUseCaseInput.getEmail());

        if (user.getIsVerified()) {
            throw new UserAlreadyVerifiedException("User already verified");
        }

        this.getPreviousUserVerificationCodeByUserId(user.getId()).ifPresent(userVerificationCode ->
                this.userVerificationCodeGateway.deleteById(userVerificationCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode(CODE_LENGTH, NUMERIC_CHARACTERS);

        final UserVerificationCode newUserVerificationCode = UserVerificationCode.newUserVerificationCode(user, codeGenerated);

        final UserVerificationCode savedUserVerificationCode = this.userVerificationCodeGateway.save(newUserVerificationCode);

        this.domainEventPublisherProvider.publishEvent(new UserVerificationCodeCreatedEvent(
                savedUserVerificationCode.getUser().getEmail(),
                savedUserVerificationCode.getCode()
        ));
    }

    private User getUserByEmail(String email) {
        return this.userGateway.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found by email: " + email));
    }

    private Optional<UserVerificationCode> getPreviousUserVerificationCodeByUserId(String userId) {
        return this.userVerificationCodeGateway.findByUserId(userId);
    }
}
