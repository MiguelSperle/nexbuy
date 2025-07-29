package com.miguelsperle.nexbuy.module.inventory.application.ports.in;

import com.miguelsperle.nexbuy.core.application.ports.in.IUseCaseWithoutReturn;
import com.miguelsperle.nexbuy.module.inventory.application.usecases.io.inputs.DecreaseInventoryUseCaseInput;

public interface IDecreaseInventoryUseCase extends IUseCaseWithoutReturn<DecreaseInventoryUseCaseInput> {
}
