package com.miguelsperle.nexbuy.module.inventory.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryMovementsUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;

public interface GetInventoryMovementsUseCase extends UseCase<GetInventoryMovementsUseCaseInput, GetInventoryMovementsUseCaseOutput> {
}
