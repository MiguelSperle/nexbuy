package com.miguelsperle.nexbuy.module.user.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.user.application.dtos.inputs.GetAddressUseCaseInput;
import com.miguelsperle.nexbuy.module.user.application.dtos.outputs.GetAddressUseCaseOutput;

public interface IGetAddressUseCase extends IUseCase<GetAddressUseCaseInput, GetAddressUseCaseOutput> {
}
