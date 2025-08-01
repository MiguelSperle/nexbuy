package com.miguelsperle.nexbuy.module.user.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.core.application.ports.in.UseCaseWithoutReturn;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateVerificationCodeUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateVerificationCodeUseCaseOutput;

public interface CreateVerificationCodeUseCase extends UseCase<CreateVerificationCodeUseCaseInput, CreateVerificationCodeUseCaseOutput> {
}
