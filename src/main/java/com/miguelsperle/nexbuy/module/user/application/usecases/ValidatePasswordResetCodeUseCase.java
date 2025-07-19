package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.ValidatePasswordResetCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IValidatePasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.core.application.utils.ExpirationUtils;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;

import java.time.LocalDateTime;

public class ValidatePasswordResetCodeUseCase implements IValidatePasswordResetCodeUseCase {
    private final IUserCodeGateway userCodeGateway;

    public ValidatePasswordResetCodeUseCase(IUserCodeGateway userCodeGateway) {
        this.userCodeGateway = userCodeGateway;
    }

    @Override
    public ValidatePasswordResetCodeUseCaseOutput execute(ValidatePasswordResetCodeUseCaseInput validatePasswordResetCodeUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(validatePasswordResetCodeUseCaseInput.code());

        if (ExpirationUtils.isExpired(userCode.getExpiresIn(), LocalDateTime.now())) {
            this.deleteUserCodeById(userCode.getId());
            throw new UserCodeExpiredException("User code has expired");
        }

        return ValidatePasswordResetCodeUseCaseOutput.from(true);
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeGateway.findByCodeAndCodeType(code, CodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> new UserCodeNotFoundException("User code not found"));
    }

    private void deleteUserCodeById(String userCodeId) {
        this.userCodeGateway.deleteById(userCodeId);
    }
}
