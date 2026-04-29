package com.miguelsperle.nexbuy.module.payment.infrastructure.listeners;

import com.miguelsperle.nexbuy.module.payment.application.commands.UpdatePaymentStatusCommand;
import com.miguelsperle.nexbuy.module.payment.application.abstractions.repositories.PaymentRepository;
import com.miguelsperle.nexbuy.module.payment.domain.entities.Payment;
import com.miguelsperle.nexbuy.shared.application.abstractions.producer.MessageProducer;
import com.miguelsperle.nexbuy.module.payment.domain.events.PaymentStatusUpdatedEvent;
import com.miguelsperle.nexbuy.shared.domain.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePaymentStatusListener {
    private final PaymentRepository paymentRepository;
    private final MessageProducer messageProducer;

    private static final String UPDATE_PAYMENT_STATUS_QUEUE = "update.payment.status.queue";

    private static final String PAYMENT_STATUS_UPDATED_EXCHANGE = "payment.status.updated.exchange";
    private static final String PAYMENT_STATUS_UPDATED_ROUTING_KEY = "payment.status.updated.routing.key";

    @RabbitListener(queues = UPDATE_PAYMENT_STATUS_QUEUE)
    public void onMessage(UpdatePaymentStatusCommand updatePaymentStatusCommand) {
        final Payment payment = this.getPaymentById(updatePaymentStatusCommand.paymentId());

        final Payment updatedPayment = payment.withPaymentStatus(updatePaymentStatusCommand.paymentStatus());

        final Payment savedPayment = this.savePayment(updatedPayment);

        final PaymentStatusUpdatedEvent paymentStatusUpdatedEvent = PaymentStatusUpdatedEvent.from(
                savedPayment.getOrderId(),
                savedPayment.getPaymentStatus().name()
        );

        this.messageProducer.publish(PAYMENT_STATUS_UPDATED_EXCHANGE, PAYMENT_STATUS_UPDATED_ROUTING_KEY, paymentStatusUpdatedEvent);
    }

    private Payment getPaymentById(String id) {
        return this.paymentRepository.findById(id).orElseThrow(() -> DomainException.with("Payment not found", 404));
    }

    private Payment savePayment(Payment payment) {
        return this.paymentRepository.save(payment);
    }
}
