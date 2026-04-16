package com.miguelsperle.nexbuy.module.inventory.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.CheckInventoryAvailabilityUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.CheckInventoryAvailabilityUseCaseOutput;

public interface CheckInventoryAvailabilityUseCase extends UseCase<CheckInventoryAvailabilityUseCaseInput, CheckInventoryAvailabilityUseCaseOutput> {
}
