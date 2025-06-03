package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.ValidateUserPasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.ValidateUserPasswordResetCodeUseCaseOutput;

public interface IValidateUserPasswordResetCodeUseCase extends IUseCase<ValidateUserPasswordResetCodeUseCaseInput, ValidateUserPasswordResetCodeUseCaseOutput> {
}
