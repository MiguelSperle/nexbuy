package com.miguelsperle.nexbuy.module.payment.application.usecases;

import com.miguelsperle.nexbuy.module.payment.application.ports.in.usecases.UpdatePaymentStatusUseCase;
import com.miguelsperle.nexbuy.module.payment.application.ports.out.persistence.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.application.usecases.io.inputs.UpdatePaymentStatusUseCaseInput;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;

public class UpdatePaymentStatusUseCaseImpl implements UpdatePaymentStatusUseCase {
    private final PaymentRepository paymentRepository;
    private final MessageProducer messageProducer;

    private static final String PAYMENT_STATUS_UPDATED_EXCHANGE = "";
    private static final String PAYMENT_STATUS_UPDATED_ROUTING_KEY = "";

    public UpdatePaymentStatusUseCaseImpl(
            PaymentRepository paymentRepository,
            MessageProducer messageProducer
    ) {
        this.paymentRepository = paymentRepository;
        this.messageProducer = messageProducer;
    }

    @Override
    public void execute(UpdatePaymentStatusUseCaseInput updatePaymentStatusUseCaseInput) {
        final Payment payment = this.getPaymentById(updatePaymentStatusUseCaseInput.paymentId());

        final Payment updatedPayment = payment.withPaymentStatus(updatePaymentStatusUseCaseInput.paymentStatus());

        throw new RuntimeException();
    }

    private Payment getPaymentById(String paymentId) {
        return this.paymentRepository.findById(paymentId)
                .orElseThrow(() -> DomainException.with("Payment not found", 404));
    }

    private void savePayment(Payment payment) {
        this.paymentRepository.save(payment);
    }
}
