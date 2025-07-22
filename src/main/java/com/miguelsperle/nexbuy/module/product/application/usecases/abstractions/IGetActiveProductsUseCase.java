package com.miguelsperle.nexbuy.module.product.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetActiveProductsUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductsUseCaseOutput;

public interface IGetActiveProductsUseCase extends IUseCase<GetActiveProductsUseCaseInput, GetActiveProductsUseCaseOutput> {
}
