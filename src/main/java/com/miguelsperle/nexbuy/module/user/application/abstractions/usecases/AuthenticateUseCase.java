package com.miguelsperle.nexbuy.module.user.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.AuthenticateUseCaseOutput;

public interface AuthenticateUseCase extends UseCase<AuthenticateUseCaseInput, AuthenticateUseCaseOutput> {
}
