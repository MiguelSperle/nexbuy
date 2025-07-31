package com.miguelsperle.nexbuy.module.inventory.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CheckInventoryAvailabilityUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.CheckInventoryAvailabilityUseCaseOutput;

public interface CheckInventoryAvailabilityUseCase extends UseCase<CheckInventoryAvailabilityUseCaseInput, CheckInventoryAvailabilityUseCaseOutput> {
}
