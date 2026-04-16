package com.miguelsperle.nexbuy.module.payment.application.abstractions.usecases;

import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.CreatePaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.CreatePaymentUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;

public interface CreatePaymentUseCase extends UseCase<CreatePaymentUseCaseInput, CreatePaymentUseCaseOutput> {
}
