package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.UpdateUserToVerifiedUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserVerificationCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserVerificationCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserToVerifiedUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserVerificationCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserVerificationCode;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UpdateUserToVerifiedUseCase implements IUpdateUserToVerifiedUseCase {
    private final IUserGateway userGateway;
    private final IUserVerificationCodeGateway userVerificationCodeGateway;
    private final ITransactionExecutor transactionExecutor;

    @Override
    public void execute(UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput) {
        final UserVerificationCode userVerificationCode = this.getUserVerificationCodeByCode(updateUserToVerifiedUseCaseInput.getCode());

        if (LocalDateTime.now().isAfter(userVerificationCode.getExpiresIn())) {
            this.userVerificationCodeGateway.deleteById(userVerificationCode.getId());
            throw new UserVerificationCodeExpiredException("User verification code expired. Please ask for a new code");
        }

        final User userUpdated = userVerificationCode.getUser().withIsVerified(true);

        this.transactionExecutor.runTransaction(() -> {
            this.userGateway.save(userUpdated);
            this.userVerificationCodeGateway.deleteById(userVerificationCode.getId());
        });
    }

    private UserVerificationCode getUserVerificationCodeByCode(String code) {
        return this.userVerificationCodeGateway.findByCode(code)
                .orElseThrow(() -> new UserVerificationCodeNotFoundException("User verification code not found by code: " + code));
    }
}
