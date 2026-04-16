package com.miguelsperle.nexbuy.module.order.application.abstractions.usecases;

import com.miguelsperle.nexbuy.module.order.application.usecases.io.inputs.GetOrderUseCaseInput;
import com.miguelsperle.nexbuy.module.order.application.usecases.io.outputs.GetOrderUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;

public interface GetOrderUseCase extends UseCase<GetOrderUseCaseInput, GetOrderUseCaseOutput> {
}
