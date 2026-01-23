package com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases;

import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;

public interface CreatePaymentUseCase extends UseCase<CreatePaymentUseCaseInput, CreatePaymentUseCaseOutput> {
}
