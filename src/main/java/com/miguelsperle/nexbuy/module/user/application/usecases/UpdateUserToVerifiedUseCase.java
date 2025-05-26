package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.UpdateUserToVerifiedUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserToVerifiedUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UpdateUserToVerifiedUseCase implements IUpdateUserToVerifiedUseCase {
    private final IUserGateway userGateway;
    private final IUserCodeGateway userCodeGateway;
    private final ITransactionExecutor transactionExecutor;

    @Override
    public void execute(UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(updateUserToVerifiedUseCaseInput.getCode());

        if (LocalDateTime.now().isAfter(userCode.getExpiresIn())) {
            this.userCodeGateway.deleteById(userCode.getId());
            throw new UserCodeExpiredException("User verification code expired. Please ask for a new code");
        }

        final User userUpdated = userCode.getUser().withIsVerified(true);

        this.transactionExecutor.runTransaction(() -> {
            this.userGateway.save(userUpdated);
            this.userCodeGateway.deleteById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeGateway.findByCodeAndCodeType(code, CodeType.USER_VERIFICATION.name())
                .orElseThrow(() -> new UserCodeNotFoundException("User verification code not found by code: " + code));
    }
}
