package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthorizationUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.AuthorizationUseCaseOutput;

public interface IAuthorizationUseCase extends IUseCase<AuthorizationUseCaseInput, AuthorizationUseCaseOutput> {
}
