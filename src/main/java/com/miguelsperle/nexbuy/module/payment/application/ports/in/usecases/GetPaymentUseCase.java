package com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases;

import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.GetPaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.GetPaymentUseCaseOutput;
import com.miguelsperle.nexbuy.shared.application.ports.in.usecases.common.UseCase;

public interface GetPaymentUseCase extends UseCase<GetPaymentUseCaseInput, GetPaymentUseCaseOutput> {
}
