package com.miguelsperle.nexbuy.module.user.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.usecases.io.outputs.GetAddressUseCaseOutput;

public interface GetAddressUseCase extends UseCase<GetAddressUseCaseInput, GetAddressUseCaseOutput> {
}
