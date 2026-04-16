package com.miguelsperle.nexbuy.module.inventory.application.abstractions.usecases;

import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoriesUseCaseOutput;

public interface GetInventoriesUseCase extends UseCase<GetInventoriesUseCaseInput, GetInventoriesUseCaseOutput> {
}
