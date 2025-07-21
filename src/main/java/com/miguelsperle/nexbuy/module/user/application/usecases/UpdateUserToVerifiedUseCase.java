package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IUpdateUserToVerifiedUseCase;
import com.miguelsperle.nexbuy.core.domain.utils.ExpirationUtils;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;

public class UpdateUserToVerifiedUseCase implements IUpdateUserToVerifiedUseCase {
    private final IUserGateway userGateway;
    private final IUserCodeGateway userCodeGateway;
    private final ITransactionExecutor transactionExecutor;

    public UpdateUserToVerifiedUseCase(
            IUserGateway userGateway,
            IUserCodeGateway userCodeGateway,
            ITransactionExecutor transactionExecutor
    ) {
        this.userGateway = userGateway;
        this.userCodeGateway = userCodeGateway;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(updateUserToVerifiedUseCaseInput.code());

        if (ExpirationUtils.isExpired(userCode.getExpiresIn(), LocalDateTime.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw UserCodeExpiredException.with("User code has expired");
        }

        final User user = this.getUserById(userCode.getUserId());

        final User updatedUser = user.withIsVerified(true);

        this.transactionExecutor.runTransaction(() -> {
            this.saveUser(updatedUser);
            this.deleteUserCodeById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeGateway.findByCodeAndCodeType(code, CodeType.USER_VERIFICATION.name())
                .orElseThrow(() -> UserCodeNotFoundException.with("User code not found"));
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userGateway.save(user);
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeGateway.deleteById(userCodeId);
    }
}
