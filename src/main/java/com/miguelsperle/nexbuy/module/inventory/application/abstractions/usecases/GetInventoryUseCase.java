package com.miguelsperle.nexbuy.module.inventory.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryUseCaseOutput;

public interface GetInventoryUseCase extends UseCase<GetInventoryUseCaseInput, GetInventoryUseCaseOutput> {
}
