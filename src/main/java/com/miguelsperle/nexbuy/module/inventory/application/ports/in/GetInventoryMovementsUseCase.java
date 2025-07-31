package com.miguelsperle.nexbuy.module.inventory.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryMovementsUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;

public interface GetInventoryMovementsUseCase extends UseCase<GetInventoryMovementsUseCaseInput, GetInventoryMovementsUseCaseOutput> {
}
