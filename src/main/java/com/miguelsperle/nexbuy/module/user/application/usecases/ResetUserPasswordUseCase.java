package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.providers.IPasswordEncryptorProvider;
import com.miguelsperle.nexbuy.core.application.ports.out.transaction.ITransactionExecutor;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ResetUserPasswordUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IResetUserPasswordUseCase;
import com.miguelsperle.nexbuy.core.domain.utils.ExpirationUtils;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserCodeRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;

public class ResetUserPasswordUseCase implements IResetUserPasswordUseCase {
    private final IUserCodeRepository userCodeRepository;
    private final IUserRepository userRepository;
    private final IPasswordEncryptorProvider passwordEncryptorProvider;
    private final ITransactionExecutor transactionExecutor;

    public ResetUserPasswordUseCase(
            IUserCodeRepository userCodeRepository,
            IUserRepository userRepository,
            IPasswordEncryptorProvider passwordEncryptorProvider,
            ITransactionExecutor transactionExecutor) {
        this.userCodeRepository = userCodeRepository;
        this.userRepository = userRepository;
        this.passwordEncryptorProvider = passwordEncryptorProvider;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(ResetUserPasswordUseCaseInput resetUserPasswordUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(resetUserPasswordUseCaseInput.code());

        if (ExpirationUtils.isExpired(userCode.getExpiresIn(), LocalDateTime.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw UserCodeExpiredException.with("User code has expired");
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
        return this.userCodeRepository.findByCodeAndCodeType(code, CodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> UserCodeNotFoundException.with("User code not found"));
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }
}
