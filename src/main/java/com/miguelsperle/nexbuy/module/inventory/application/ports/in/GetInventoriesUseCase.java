package com.miguelsperle.nexbuy.module.inventory.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoriesUseCaseOutput;

public interface GetInventoriesUseCase extends UseCase<GetInventoriesUseCaseInput, GetInventoriesUseCaseOutput> {
}
