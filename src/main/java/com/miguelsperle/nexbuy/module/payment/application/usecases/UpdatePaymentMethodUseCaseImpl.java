package com.miguelsperle.nexbuy.module.payment.application.usecases;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.UpdatePaymentMethodUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence.PaymentMethodRepository;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.UpdatePaymentMethodUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.domain.entities.PaymentMethod;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import com.miguelsperle.nexbuy.shared.domain.exception.NotFoundException;

public class UpdatePaymentMethodUseCaseImpl implements UpdatePaymentMethodUseCase {
    private final PaymentMethodRepository paymentMethodRepository;

    public UpdatePaymentMethodUseCaseImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public void execute(UpdatePaymentMethodUseCaseInput updatePaymentMethodUseCaseInput) {
        final PaymentMethod paymentMethod = this.getPaymentMethodById(updatePaymentMethodUseCaseInput.paymentMethodId());

        if (!updatePaymentMethodUseCaseInput.name().equalsIgnoreCase(paymentMethod.getName())) {
            if (this.verifyPaymentMethodAlreadyExistsByName(updatePaymentMethodUseCaseInput.name())) {
                throw DomainException.with("Payment method with this name already exists", 409);
            }
        }

        final PaymentMethod updatedPaymentMethod = paymentMethod.withName(updatePaymentMethodUseCaseInput.name());

        this.savePaymentMethod(updatedPaymentMethod);
    }

    private PaymentMethod getPaymentMethodById(String paymentMethodId) {
        return this.paymentMethodRepository.findById(paymentMethodId).orElseThrow(() -> NotFoundException.with("Payment not found"));
    }

    private boolean verifyPaymentMethodAlreadyExistsByName(String name) {
        return this.paymentMethodRepository.existsByName(name);
    }

    private void savePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethodRepository.save(paymentMethod);
    }
}
