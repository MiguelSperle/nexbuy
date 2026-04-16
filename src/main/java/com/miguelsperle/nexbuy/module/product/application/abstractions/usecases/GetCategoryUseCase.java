package com.miguelsperle.nexbuy.module.product.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;

public interface GetCategoryUseCase extends UseCase<GetCategoryUseCaseInput, GetCategoryUseCaseOutput> {
}
