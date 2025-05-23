package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthenticateUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthenticateUseCaseOutput;

public interface IAuthenticateUseCase extends IUseCase<AuthenticateUseCaseInput, AuthenticateUseCaseOutput> {
}
