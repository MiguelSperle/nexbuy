package com.miguelsperle.nexbuy.module.user.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;

public interface RefreshTokenUseCase extends UseCase<RefreshTokenUseCaseInput, RefreshTokenUseCaseOutput> {
}
