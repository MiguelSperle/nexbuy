package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoriesUseCaseInput;
import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoriesUseCaseOutput;

public interface GetCategoriesUseCase extends UseCase<GetCategoriesUseCaseInput, GetCategoriesUseCaseOutput> {
}
