package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.CreateRefreshTokenUseCaseOutput;

public interface ICreateRefreshTokenUseCase extends IUseCase<CreateRefreshTokenUseCaseInput, CreateRefreshTokenUseCaseOutput> {
}
