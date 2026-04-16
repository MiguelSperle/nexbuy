package com.miguelsperle.nexbuy.module.product.application.abstractions.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoriesUseCaseInput;
import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;

public interface GetCategoriesUseCase extends UseCase<GetCategoriesUseCaseInput, GetCategoriesUseCaseOutput> {
}
