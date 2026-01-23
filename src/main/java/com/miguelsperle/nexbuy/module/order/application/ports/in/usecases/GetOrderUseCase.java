package com.miguelsperle.nexbuy.module.order.application.ports.in.usecases;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrderUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;

public interface GetOrderUseCase extends UseCase<GetOrderUseCaseInput, GetOrderUseCaseOutput> {
}
