package com.miguelsperle.nexbuy.module.order.application.abstractions.usecases;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrdersUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrdersUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;

public interface GetOrdersUseCase extends UseCase<GetOrdersUseCaseInput, GetOrdersUseCaseOutput> {
}
