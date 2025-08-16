package com.miguelsperle.nexbuy.module.user.application.ports.in.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.CreateRefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.CreateRefreshTokenUseCaseOutput;

public interface CreateRefreshTokenUseCase extends UseCase<CreateRefreshTokenUseCaseInput, CreateRefreshTokenUseCaseOutput> {
}
