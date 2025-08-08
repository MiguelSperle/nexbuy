package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductUseCaseOutput;

public interface GetProductUseCase extends UseCase<GetProductUseCaseInput, GetProductUseCaseOutput> {
}
