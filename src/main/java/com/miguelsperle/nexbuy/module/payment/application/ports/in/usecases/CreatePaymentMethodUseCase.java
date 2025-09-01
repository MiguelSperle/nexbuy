package com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases;

import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentMethodUseCaseInput;
import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCaseWithoutReturn;

public interface CreatePaymentMethodUseCase extends UseCaseWithoutReturn<CreatePaymentMethodUseCaseInput> {
}
