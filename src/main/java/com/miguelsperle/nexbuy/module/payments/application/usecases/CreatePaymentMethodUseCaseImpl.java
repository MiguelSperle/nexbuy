package com.miguelsperle.nexbuy.module.payments.application.usecases;

import com.miguelsperle.nexbuy.module.payments.application.ports.in.usecases.CreatePaymentMethodUseCase;
import com.miguelsperle.nexbuy.module.payments.application.ports.out.persistence.PaymentMethodRepository;
import com.miguelsperle.nexbuy.module.payments.application.usecases.io.inputs.CreatePaymentMethodUseCaseInput;
import com.miguelsperle.nexbuy.module.payments.domain.entities.PaymentMethod;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class CreatePaymentMethodUseCaseImpl implements CreatePaymentMethodUseCase {
    private final PaymentMethodRepository paymentMethodRepository;

    public CreatePaymentMethodUseCaseImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public void execute(CreatePaymentMethodUseCaseInput createPaymentMethodUseCaseInput) {
        if (this.verifyPaymentMethodAlreadyExistsByName(createPaymentMethodUseCaseInput.name())) {
            throw DomainException.with("Payment method with this name already exists", 409);
        }

        final PaymentMethod newPaymentMethod = PaymentMethod.newPaymentMethod(createPaymentMethodUseCaseInput.name());

        this.savePaymentMethod(newPaymentMethod);
    }

    private boolean verifyPaymentMethodAlreadyExistsByName(String name) {
        return this.paymentMethodRepository.existsByName(name);
    }

    private void savePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethodRepository.save(paymentMethod);
    }
}
