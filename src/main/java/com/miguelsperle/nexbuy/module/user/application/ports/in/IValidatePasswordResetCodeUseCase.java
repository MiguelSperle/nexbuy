package com.miguelsperle.nexbuy.module.user.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;

public interface IValidatePasswordResetCodeUseCase extends IUseCase<ValidatePasswordResetCodeUseCaseInput, ValidatePasswordResetCodeUseCaseOutput> {
}
