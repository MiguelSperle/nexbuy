package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;

public interface GetColorUseCase extends UseCase<GetColorUseCaseInput, GetColorUseCaseOutput> {
}
