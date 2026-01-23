package com.miguelsperle.nexbuy.module.product.application.ports.in.usecases;

import com.miguelsperle.nexbuy.common.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;

public interface GetProductsUseCase extends UseCase<GetProductsUseCaseInput, GetProductsUseCaseOutput> {
}
