package com.miguelsperle.nexbuy.module.product.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetBrandUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetBrandUseCaseOutput;

public interface IGetBrandUseCase extends IUseCase<GetBrandUseCaseInput, GetBrandUseCaseOutput> {
}
