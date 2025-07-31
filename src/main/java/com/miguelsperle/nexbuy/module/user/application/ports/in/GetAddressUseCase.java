package com.miguelsperle.nexbuy.module.user.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressUseCaseOutput;

public interface GetAddressUseCase extends UseCase<GetAddressUseCaseInput, GetAddressUseCaseOutput> {
}
