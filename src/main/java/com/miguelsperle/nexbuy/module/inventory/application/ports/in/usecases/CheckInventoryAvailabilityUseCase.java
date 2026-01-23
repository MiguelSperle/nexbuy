package com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CheckInventoryAvailabilityUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.CheckInventoryAvailabilityUseCaseOutput;

public interface CheckInventoryAvailabilityUseCase extends UseCase<CheckInventoryAvailabilityUseCaseInput, CheckInventoryAvailabilityUseCaseOutput> {
}
