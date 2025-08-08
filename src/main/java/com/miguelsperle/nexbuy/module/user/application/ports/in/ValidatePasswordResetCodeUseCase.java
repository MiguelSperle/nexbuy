package com.miguelsperle.nexbuy.module.user.application.ports.in;

import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.ValidatePasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.ValidatePasswordResetCodeUseCaseOutput;

public interface ValidatePasswordResetCodeUseCase extends UseCase<ValidatePasswordResetCodeUseCaseInput, ValidatePasswordResetCodeUseCaseOutput> {
}
