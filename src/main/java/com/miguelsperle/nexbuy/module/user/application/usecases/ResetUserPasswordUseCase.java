package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.ResetUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
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
            this.userCodeGateway.deleteById(userCode.getId());
            throw new UserCodeExpiredException("User code has expired");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(resetUserPasswordUseCaseInput.password());

        final User updatedUser = userCode.getUser().withPassword(encodedPassword);

        this.transactionExecutor.runTransaction(() -> {
            this.userGateway.save(updatedUser);
            this.userCodeGateway.deleteById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeGateway.findByCodeAndCodeType(code, CodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> new UserCodeNotFoundException("User code not found"));
    }
}
