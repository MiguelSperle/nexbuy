package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.domain.abstractions.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.domain.abstractions.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.dtos.ResetUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IResetUserPasswordUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ResetUserPasswordUseCase implements IResetUserPasswordUseCase {
    private final IUserCodeGateway userCodeGateway;
    private final IUserGateway userGateway;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ITransactionExecutor transactionExecutor;

    @Override
    public void execute(ResetUserPasswordUseCaseInput resetUserPasswordUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(resetUserPasswordUseCaseInput.getCode());

        if (LocalDateTime.now().isAfter(userCode.getExpiresIn())) {
            this.userCodeGateway.deleteById(userCode.getId());
            throw new UserCodeExpiredException("User code has expired");
        }

        final String encodedPassword = this.passwordEncryptorProvider.encode(resetUserPasswordUseCaseInput.getPassword());

        final User userUpdated = userCode.getUser().withPassword(encodedPassword);

        this.transactionExecutor.runTransaction(() -> {
            this.userGateway.save(userUpdated);
            this.userCodeGateway.deleteById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeGateway.findByCodeAndCodeType(code, CodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> new UserCodeNotFoundException("User code not found"));
    }
}
