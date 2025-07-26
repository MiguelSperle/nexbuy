package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetProductsUseCaseOutput;

public interface IGetProductsUseCase extends IUseCase<GetProductsUseCaseInput, GetProductsUseCaseOutput> {
}
