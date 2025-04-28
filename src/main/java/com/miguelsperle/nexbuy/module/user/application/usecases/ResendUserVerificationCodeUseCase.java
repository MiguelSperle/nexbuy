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

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class ResendUserVerificationCodeUseCase implements IResendUserVerificationCodeUseCase {
    private final IUserVerificationCodeGateway userVerificationCodeGateway;
    private final IUserGateway userGateway;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ICodeProvider codeProvider;

    @Override
    public void execute(ResendUserVerificationCodeUseCaseInput resendUserVerificationCodeUseCaseInput) {
        final User user = this.getUserByEmail(resendUserVerificationCodeUseCaseInput.getEmail());

        if (user.getIsVerified()) {
            throw new UserAlreadyVerifiedException("User already verified");
        }

        this.deleteUserVerificationCode(user.getId());

        final String codeGenerated = this.codeProvider.generateCode(6, "0123456789");

        final UserVerificationCode newUserVerificationCode = UserVerificationCode.newUserVerificationCode(codeGenerated, user, LocalDateTime.now().plusMinutes(15));

        final UserVerificationCode savedUserVerificationCode = this.userVerificationCodeGateway.save(newUserVerificationCode);

        this.domainEventPublisherProvider.publish(new UserVerificationCodeCreatedEvent(
                savedUserVerificationCode.getUser().getEmail(),
                savedUserVerificationCode.getCode()
        ));
    }

    private User getUserByEmail(String email) {
        return this.userGateway.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void deleteUserVerificationCode(String userId) {
        final Optional<UserVerificationCode> userVerificationCode = this.userVerificationCodeGateway.findByUserId(userId);

        userVerificationCode.ifPresent(verificationCode -> this.userVerificationCodeGateway.deleteById(verificationCode.getId()));
    }
}
