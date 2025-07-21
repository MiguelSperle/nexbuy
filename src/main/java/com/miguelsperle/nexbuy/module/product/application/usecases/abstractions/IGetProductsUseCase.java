package com.miguelsperle.nexbuy.module.product.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;

public interface IGetProductsUseCase extends IUseCase<GetProductsUseCaseInput, GetProductsUseCaseOutput> {
}
