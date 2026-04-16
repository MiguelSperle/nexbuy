package com.miguelsperle.nexbuy.module.product.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;

public interface GetProductsUseCase extends UseCase<GetProductsUseCaseInput, GetProductsUseCaseOutput> {
}
