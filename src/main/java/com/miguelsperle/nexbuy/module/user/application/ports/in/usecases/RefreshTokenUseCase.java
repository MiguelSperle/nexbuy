package com.miguelsperle.nexbuy.module.user.application.ports.in.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.RefreshTokenUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.RefreshTokenUseCaseOutput;

public interface RefreshTokenUseCase extends UseCase<RefreshTokenUseCaseInput, RefreshTokenUseCaseOutput> {
}
