package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.RefreshTokenUseCaseOutput;

public interface IRefreshTokenUseCase extends IUseCase<RefreshTokenUseCaseInput, RefreshTokenUseCaseOutput> {
}
