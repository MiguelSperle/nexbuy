package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.domain.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.ports.in.IValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.core.domain.utils.ExpirationUtils;
import com.miguelsperle.nexbuy.module.user.application.ports.out.persistence.IUserCodeRepository;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;

public class ValidatePasswordResetCodeUseCase implements IValidatePasswordResetCodeUseCase {
    private final IUserCodeRepository userCodeRepository;

    public ValidatePasswordResetCodeUseCase(IUserCodeRepository userCodeRepository) {
        this.userCodeRepository = userCodeRepository;
    }

    @Override
    public ValidatePasswordResetCodeUseCaseOutput execute(ValidatePasswordResetCodeUseCaseInput validatePasswordResetCodeUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(validatePasswordResetCodeUseCaseInput.code());

        if (ExpirationUtils.isExpired(userCode.getExpiresIn(), LocalDateTime.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw UserCodeExpiredException.with("User code has expired");
        }

        return ValidatePasswordResetCodeUseCaseOutput.from(true);
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeRepository.findByCodeAndCodeType(code, CodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> UserCodeNotFoundException.with("User code not found"));
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeRepository.deleteById(userCodeId);
    }
}
