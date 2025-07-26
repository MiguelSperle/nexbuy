package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;

public interface IGetCategoryUseCase extends IUseCase<GetCategoryUseCaseInput, GetCategoryUseCaseOutput> {
}
