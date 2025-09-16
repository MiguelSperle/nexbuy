package com.miguelsperle.nexbuy.module.payment.application.usecases;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.GetPaymentUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.GetPaymentUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.outputs.GetPaymentUseCaseOutput;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class GetPaymentUseCaseImpl implements GetPaymentUseCase {
    private final PaymentRepository paymentRepository;

    public GetPaymentUseCaseImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public GetPaymentUseCaseOutput execute(GetPaymentUseCaseInput getPaymentUseCaseInput) {
        final Payment payment = this.getPaymentByOrderId(getPaymentUseCaseInput.orderId());

        return GetPaymentUseCaseOutput.from(payment);
    }

    private Payment getPaymentByOrderId(String orderId) {
        return this.paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> DomainException.with("Payment not found", 404));
    }
}
