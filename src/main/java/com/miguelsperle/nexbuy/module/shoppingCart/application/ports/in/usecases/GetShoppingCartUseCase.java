package com.miguelsperle.nexbuy.module.shoppingCart.application.ports.in.usecases;

import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.inputs.GetShoppingCartUseCaseInput;
import com.miguelsperle.nexbuy.module.shoppingCart.application.usecases.io.outputs.GetShoppingCartUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;

public interface GetShoppingCartUseCase extends UseCase<GetShoppingCartUseCaseInput, GetShoppingCartUseCaseOutput> {
}
