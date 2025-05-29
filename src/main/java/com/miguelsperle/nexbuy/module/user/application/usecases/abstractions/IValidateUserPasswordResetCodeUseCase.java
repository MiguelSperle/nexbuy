package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.ValidateUserPasswordResetCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.ValidateUserPasswordResetCodeUseCaseOutput;

public interface IValidateUserPasswordResetCodeUseCase extends IUseCase<ValidateUserPasswordResetCodeUseCaseInput, ValidateUserPasswordResetCodeUseCaseOutput> {
}
