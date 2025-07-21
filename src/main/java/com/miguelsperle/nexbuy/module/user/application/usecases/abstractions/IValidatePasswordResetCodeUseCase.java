package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;

public interface IValidatePasswordResetCodeUseCase extends IUseCase<ValidatePasswordResetCodeUseCaseInput, ValidatePasswordResetCodeUseCaseOutput> {
}
