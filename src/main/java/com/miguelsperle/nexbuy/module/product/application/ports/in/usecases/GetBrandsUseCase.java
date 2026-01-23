package com.miguelsperle.nexbuy.module.product.application.ports.in.usecases;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandsUseCaseInput;
import com.miguelsperle.nexbuy.common.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;

public interface GetBrandsUseCase extends UseCase<GetBrandsUseCaseInput, GetBrandsUseCaseOutput> {
}
