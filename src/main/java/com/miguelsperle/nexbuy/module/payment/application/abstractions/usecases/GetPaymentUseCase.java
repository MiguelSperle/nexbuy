package com.miguelsperle.nexbuy.module.payment.application.abstractions.usecases;

import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.GetPaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.GetPaymentUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.abstractions.usecases.UseCase;

public interface GetPaymentUseCase extends UseCase<GetPaymentUseCaseInput, GetPaymentUseCaseOutput> {
}
