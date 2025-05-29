package com.miguelsperle.nexbuy.module.user.application.usecases;

import com.miguelsperle.nexbuy.module.user.application.dtos.ValidateUserPasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.ValidateUserPasswordResetCodeUseCaseOutput;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeExpiredException;
import com.miguelsperle.nexbuy.module.user.application.exceptions.UserCodeNotFoundException;
import com.miguelsperle.nexbuy.module.user.application.usecases.abstractions.IValidateUserPasswordResetCodeUseCase;
import com.miguelsperle.nexbuy.module.user.domain.abstractions.gateways.IUserCodeGateway;
import com.miguelsperle.nexbuy.module.user.domain.entities.UserCode;
import com.miguelsperle.nexbuy.module.user.domain.enums.CodeType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ValidateUserPasswordResetCodeUseCase implements IValidateUserPasswordResetCodeUseCase {
    private final IUserCodeGateway userCodeGateway;

    @Override
    public ValidateUserPasswordResetCodeUseCaseOutput execute(ValidateUserPasswordResetCodeUseCaseInput validateUserPasswordResetCodeUseCaseInput) {
        final UserCode userCode = this.getUserCodeByCodeAndCodeType(validateUserPasswordResetCodeUseCaseInput.getCode());

        if (LocalDateTime.now().isAfter(userCode.getExpiresIn())) {
            this.userCodeGateway.deleteById(userCode.getId());
            throw new UserCodeExpiredException("User code has expired");
        }

        return new ValidateUserPasswordResetCodeUseCaseOutput(true);
    }

    private UserCode getUserCodeByCodeAndCodeType(String code) {
        return this.userCodeGateway.findByCodeAndCodeType(code, CodeType.PASSWORD_RESET.name())
                .orElseThrow(() -> new UserCodeNotFoundException("User code not found"));
    }
}
