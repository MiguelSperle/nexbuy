package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetCategoryUseCaseInput;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetCategoryUseCaseOutput;

public interface GetCategoryUseCase extends UseCase<GetCategoryUseCaseInput, GetCategoryUseCaseOutput> {
}
