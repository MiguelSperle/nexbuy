package com.miguelsperle.nexbuy.module.product.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetActiveProductUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetActiveProductUseCaseOutput;

public interface IGetActiveProductUseCase extends IUseCase<GetActiveProductUseCaseInput, GetActiveProductUseCaseOutput> {
}
