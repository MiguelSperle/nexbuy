package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.domain.abstractions.usecases.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateUserUseCaseOutput;

public interface ICreateUserUseCase extends IUseCase<CreateUserUseCaseInput, CreateUserUseCaseOutput> {
}
