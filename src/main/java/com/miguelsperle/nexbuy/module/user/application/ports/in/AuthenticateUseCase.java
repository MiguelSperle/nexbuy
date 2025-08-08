package com.miguelsperle.nexbuy.module.user.application.ports.in;

import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.AuthenticateUseCaseOutput;

public interface AuthenticateUseCase extends UseCase<AuthenticateUseCaseInput, AuthenticateUseCaseOutput> {
}
