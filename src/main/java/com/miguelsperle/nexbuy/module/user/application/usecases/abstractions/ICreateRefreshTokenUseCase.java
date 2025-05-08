package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.CreateRefreshTokenUseCaseOutput;

public interface ICreateRefreshTokenUseCase extends IUseCase<CreateRefreshTokenUseCaseInput, CreateRefreshTokenUseCaseOutput> {
}
