package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.ICodeProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IDomainEventPublisherProvider;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.ResendUserVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserAlreadyVerifiedException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResendUserVerificationCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import com.miguelsperle.nexbuy.module.user.domain.events.UserCodeCreatedEvent;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ResendUserVerificationCodeUseCase implements IResendUserVerificationCodeUseCase {
    private final IUserCodeGateway userCodeGateway;
    private final IUserGateway userGateway;
    private final IDomainEventPublisherProvider domainEventPublisherProvider;
    private final ICodeProvider codeProvider;

    @Override
    public void execute(ResendUserVerificationCodeUseCaseInput resendUserVerificationCodeUseCaseInput) {
        final User user = this.getUserByEmail(resendUserVerificationCodeUseCaseInput.getEmail());

        if (user.getIsVerified()) {
            throw new UserAlreadyVerifiedException("User already verified");
        }

        this.getPreviousUserCodeByUserIdAndCodeType(user.getId()).ifPresent(userCode ->
                this.userCodeGateway.deleteById(userCode.getId())
        );

        final String codeGenerated = this.codeProvider.generateCode();

        final UserCode newUserCode = UserCode.newUserCode(user, codeGenerated, CodeType.USER_VERIFICATION);

        final UserCode savedUserCode = this.userCodeGateway.save(newUserCode);

        this.domainEventPublisherProvider.publishEvent(new UserCodeCreatedEvent(
                savedUserCode.getUser().getEmail(),
                savedUserCode.getCode(),
                savedUserCode.getCodeType()
        ));
    }

    private User getUserByEmail(String email) {
        return this.userGateway.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Optional<UserCode> getPreviousUserCodeByUserIdAndCodeType(String userId) {
        return this.userCodeGateway.findByUserIdAndCodeType(userId, CodeType.USER_VERIFICATION.name());
    }
}
