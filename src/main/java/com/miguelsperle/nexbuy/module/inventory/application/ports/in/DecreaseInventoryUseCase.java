package com.miguelsperle.nexbuy.module.inventory.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.UseCaseWithoutReturn;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;

public interface DecreaseInventoryUseCase extends UseCaseWithoutReturn<DecreaseInventoryUseCaseInput> {
}
