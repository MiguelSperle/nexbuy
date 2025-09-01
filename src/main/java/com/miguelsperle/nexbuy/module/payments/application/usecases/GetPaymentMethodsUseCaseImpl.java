package com.miguelsperle.nexbuy.module.payments.application.usecases;

import com.miguelsperle.nexbuy.module.payments.application.ports.in.usecases.GetPaymentMethodsUseCase;
import com.miguelsperle.nexbuy.module.payments.application.ports.out.persistence.PaymentMethodRepository;
import com.miguelsperle.nexbuy.module.payments.application.usecases.io.outputs.GetPaymentMethodsUseCaseOutput;
import com.miguelsperle.nexbuy.module.payments.domain.entities.PaymentMethod;

import java.util.List;

public class GetPaymentMethodsUseCaseImpl implements GetPaymentMethodsUseCase {
    private final PaymentMethodRepository paymentMethodRepository;

    public GetPaymentMethodsUseCaseImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public GetPaymentMethodsUseCaseOutput execute() {
        final List<PaymentMethod> paymentMethods = this.getAllPaymentMethods();

        return GetPaymentMethodsUseCaseOutput.from(paymentMethods);
    }

    private List<PaymentMethod> getAllPaymentMethods() {
        return this.paymentMethodRepository.findAll();
    }
}
