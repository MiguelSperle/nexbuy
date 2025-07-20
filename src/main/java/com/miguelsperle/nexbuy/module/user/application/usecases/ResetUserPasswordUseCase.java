package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.ResetUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResetUserPasswordUseCase;
import com.miguelsperle.nexbuy.core.application.utils.ExpirationUtils;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;

public class ResetUserPasswordUseCase implements IResetUserPasswordUseCase {
    private final IUserCodeGateway userCodeGateway;
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ITransactionExecutor transactionExecutor;

    public ResetUserPasswordUseCase(
            IUserCodeGateway userCodeGateway,
            IUserGateway userGateway,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ITransactionExecutor transactionExecutor) {
        this.userCodeGateway = userCodeGateway;
        this.userGateway = userGateway;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(ResetUserPasswordUseCaseInput resetUserPasswordUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(resetUserPasswordUseCaseInput.code());

        if (ExpirationUtils.isExpired(userCode.getExpiresIn(), LocalDateTime.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw new UserCodeExpiredException("User code has expired");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(resetUserPasswordUseCaseInput.password());

        final User user = this.getUserById(userCode.getUserId());

        final User updatedUser = user.withPassword(encodedPassword);

        this.transactionExecutor.runTransaction(() -> {
            this.saveUser(updatedUser);
            this.deleteUserCodeById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeGateway.findByCodeAndCodeType(code, CodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> new UserCodeNotFoundException("User code not found"));
    }

    private User getUserById(String userId) {
        return this.userGateway.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private void saveUser(User user) {
        this.userGateway.save(user);
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeGateway.deleteById(userCodeId);
    }
}
