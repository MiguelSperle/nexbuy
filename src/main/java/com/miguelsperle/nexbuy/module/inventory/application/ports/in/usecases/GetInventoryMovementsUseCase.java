package com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoryMovementsUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoryMovementsUseCaseOutput;

public interface GetInventoryMovementsUseCase extends UseCase<GetInventoryMovementsUseCaseInput, GetInventoryMovementsUseCaseOutput> {
}
