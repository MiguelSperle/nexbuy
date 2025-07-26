package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;

public interface IGetBrandUseCase extends IUseCase<GetBrandUseCaseInput, GetBrandUseCaseOutput> {
}
