package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.shared.domain.utils.TimeUtils;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.ValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.UserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.UserCodeType;

import java.time.LocalDateTime;

public class ValidatePasswordResetCodeUseCaseImpl implements ValidatePasswordResetCodeUseCase {
    private final UserCodeRepository userCodeRepository;

    public ValidatePasswordResetCodeUseCaseImpl(UserCodeRepository userCodeRepository) {
        this.userCodeRepository = userCodeRepository;
    }

    @Override
    public ValidatePasswordResetCodeUseCaseOutput execute(ValidatePasswordResetCodeUseCaseInput validatePasswordResetCodeUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(validatePasswordResetCodeUseCaseInput.code());

        if (TimeUtils.isExpired(userCode.getExpiresIn(), LocalDateTime.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw UserCodeExpiredException.with("User code has expired");
        }

        return ValidatePasswordResetCodeUseCaseOutput.from(true);
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, UserCodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> UserCodeNotFoundException.with("User code not found"));
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }
}
