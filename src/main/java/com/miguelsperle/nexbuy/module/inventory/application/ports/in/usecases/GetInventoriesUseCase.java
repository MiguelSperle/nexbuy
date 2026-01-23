package com.miguelsperle.nexbuy.module.inventory.application.ports.in.usecases;

import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.GetInventoriesUseCaseInput;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.outputs.GetInventoriesUseCaseOutput;

public interface GetInventoriesUseCase extends UseCase<GetInventoriesUseCaseInput, GetInventoriesUseCaseOutput> {
}
