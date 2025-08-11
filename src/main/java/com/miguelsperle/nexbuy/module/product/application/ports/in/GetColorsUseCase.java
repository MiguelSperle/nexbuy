package com.miguelsperle.nexbuy.module.product.application.ports.in;

import com.miguelsperle.nexbuy.module.product.application.usecases.io.inputs.GetColorsUseCaseInput;
import com.miguelsperle.nexbuy.shared.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.product.application.usecases.io.outputs.GetColorsUseCaseOutput;

public interface GetColorsUseCase extends UseCase<GetColorsUseCaseInput, GetColorsUseCaseOutput> {
}
