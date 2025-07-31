package com.miguelsperle.nexbuy.module.user.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCaseWithoutArgument;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAuthenticatedUserUseCaseOutput;

public interface GetAuthenticatedUserUseCase extends UseCaseWithoutArgument<GetAuthenticatedUserUseCaseOutput> {
}
