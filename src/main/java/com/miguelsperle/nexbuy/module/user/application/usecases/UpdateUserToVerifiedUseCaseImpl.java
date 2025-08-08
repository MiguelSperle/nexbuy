package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.out.transaction.TransactionExecutor;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;
import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.UpdateUserToVerifiedUseCaseInput;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserStatus;
import com.miguelsperle.nexbuy.module.user.application.ports.in.UpdateUserToVerifiedUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserRepository;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.User;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;

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
            throw DomainException.with("User code has expired", 410);
        }

        final User user = this.getUserById(userCode.getUserId());

        final User updatedUser = user.withUserStatus(UserStatus.VERIFIED);

        this.transactionExecutor.runTransaction(() -> {
            this.saveUser(updatedUser);
            this.deleteUserCodeById(userCode.getId());
        });
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, UserCodeType.USER_VERIFICATION.name())
                .orElseThrow(() -> NotFoundException.with("User code not found"));
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> NotFoundException.with("User not found"));
    }

    private void saveUser(User user) {
        this.userRepository.save(user);
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }
}
