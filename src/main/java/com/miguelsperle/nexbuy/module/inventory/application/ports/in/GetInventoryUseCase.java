package com.miguelsperle.nexbuy.module.inventory.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;

public interface GetInventoryUseCase extends UseCase<GetInventoryUseCaseInput, GetInventoryUseCaseOutput> {
}
