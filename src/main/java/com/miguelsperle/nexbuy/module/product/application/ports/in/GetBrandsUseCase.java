package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetBrandsUseCaseInput;
import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetBrandsUseCaseOutput;

public interface GetBrandsUseCase extends UseCase<GetBrandsUseCaseInput, GetBrandsUseCaseOutput> {
}
