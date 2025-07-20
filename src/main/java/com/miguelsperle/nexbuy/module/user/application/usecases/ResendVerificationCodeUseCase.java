package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.ResendVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyVerifiedException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResendVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;

import java.util.Optional;

public class ResendVerificationCodeUseCase implements IResendVerificationCodeUseCase {
    private final IUserCodeGateway userCodeGateway;
    private final IUserGateway userGateway;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ICodeProvider codeProvider;

    public ResendVerificationCodeUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            IDomainEventPublisherProvider domainEventPublisherProvider,
            ICodeProvider codeProvider
    ) {
        this.userCodeGateway = userCodeGateway;
        this.userGateway = userGateway;
        this.domainEventPublisherProvider = domainEventPublisherProvider;
        this.codeProvider = codeProvider;
    }

    @Override
    public void execute(ResendVerificationCodeUseCaseInput resendVerificationCodeUseCaseInput) {
        final User user = this.getUserByEmail(resendVerificationCodeUseCaseInput.email());

        if (user.getIsVerified()) {
            throw new UserAlreadyVerifiedException("User already verified");
        }

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.deleteUserCodeById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(user.getId(), codeGenerated, CodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.saveUserCode(newUserCode);

        this.domainEventPublisherProvider.publishEvent(UserCodeCreatedEvent.from(savedUserCode.getId()));
    }

    private User getUserByEmail(String email) {
        return this.userGateway.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeGateway.findByUserIdAndCodeType(userId, CodeType.USER_VERIFICATION.name());
    }

    private UserCode saveUserCode(UserCode userCode) {
        return this.userCodeGateway.save(userCode);
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeGateway.deleteById(userCodeId);
    }
}
