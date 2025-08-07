package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.core.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.core.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.UpdateUserToVerifiedUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;

public class UpdateUserToVerifiedUseCaseImpl implements UpdateUserToVerifiedUseCase {
    private final UserRepository userRepository;
    private final UserCodeRepository userCodeRepository;
    private final TransactionExecutor transactionExecutor;

    public UpdateUserToVerifiedUseCaseImpl(
            UserRepository userRepository,
            UserCodeRepository userCodeRepository,
            TransactionExecutor transactionExecutor
    ) {
        this.userRepository = userRepository;
        this.userCodeRepository = userCodeRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Override
    public void execute(UpdateUserToVerifiedUseCaseInput updateUserToVerifiedUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(updateUserToVerifiedUseCaseInput.code());

        if (TimeUtils.isExpired(userCode.getExpiresIn(), LocalDateTime.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw UserCodeExpiredException.with("User code has expired");
        }

        final User user = this.getUserById(userCode.getUserId());

        final User updatedUser = user.withUserStatus(UserStatus.VERIFIED);

        this.transactionExecutor.runTransaction(() -> {
            this.saveUser(updatedUser);
            this.deleteUserCodeById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, CodeType.USER_VERIFICATION.name())
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
