package com.miguelsperle.nexbuy.module.user.application.ports.in.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateVerificationCodeUseCaseOutput;

public interface CreateVerificationCodeUseCase extends UseCase<CreateVerificationCodeUseCaseInput, CreateVerificationCodeUseCaseOutput> {
}
