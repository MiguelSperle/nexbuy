package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorUseCaseOutput;

public interface IGetColorUseCase extends IUseCase<GetColorUseCaseInput, GetColorUseCaseOutput> {
}
