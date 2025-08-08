package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandUseCaseOutput;

public interface GetBrandUseCase extends UseCase<GetBrandUseCaseInput, GetBrandUseCaseOutput> {
}
