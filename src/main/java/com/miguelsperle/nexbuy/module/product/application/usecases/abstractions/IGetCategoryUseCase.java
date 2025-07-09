package com.miguelsperle.nexbuy.module.product.application.usecases.abstractions;

import com.miguelsperle.nexbuy.core.application.usecases.abstractions.IUseCase;
import com.miguelsperle.nexbuy.module.product.application.dtos.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.dtos.outputs.GetCategoryUseCaseOutput;

public interface IGetCategoryUseCase extends IUseCase<GetCategoryUseCaseInput, GetCategoryUseCaseOutput> {
}
