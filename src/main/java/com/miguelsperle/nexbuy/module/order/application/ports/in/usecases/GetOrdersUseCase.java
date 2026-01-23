package com.miguelsperle.nexbuy.module.order.application.ports.in.usecases;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrdersUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrdersUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;

public interface GetOrdersUseCase extends UseCase<GetOrdersUseCaseInput, GetOrdersUseCaseOutput> {
}
