package com.miguelsperle.nexbuy.module.user.infrastructure.dtos.responses;

import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.ValidateUserPasswordResetCodeUseCaseOutput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateUserPasswordResetCodeResponse {
    private Boolean codeIsValid;

    public static ValidateUserPasswordResetCodeResponse fromOutput(ValidateUserPasswordResetCodeUseCaseOutput validateUserPasswordResetCodeUseCaseOutput) {
        return new ValidateUserPasswordResetCodeResponse(validateUserPasswordResetCodeUseCaseOutput.getCodeIsValid());
    }
}
