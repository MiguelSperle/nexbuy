package com.miguelsperle.nexbuy.module.payment.infrastructure.adapters.in.web.controllers.webhooks;

import com.miguelsperle.nexbuy.module.payment.application.commands.UpdatePaymentStatusCommand;
import com.miguelsperle.nexbuy.module.payment.domain.enums.PaymentStatus;
import com.miguelsperle.nexbuy.shared.application.ports.out.producer.MessageProducer;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/payments/webhook")
@RequiredArgsConstructor
public class PaymentWebhookController {
    private final MessageProducer messageProducer;

    @Value("${spring.stripe.api.webhook.secret.key}")
    private String stripeApiWebhookSecretKey;

    private static final String UPDATE_PAYMENT_STATUS_EXCHANGE = "update.payment.status.exchange";
    private static final String UPDATE_PAYMENT_STATUS_ROUTING_KEY = "update.payment.status.routing.key";

    @PostMapping
    public ResponseEntity<Void> handleWebhook(HttpServletRequest request) throws IOException, SignatureVerificationException {
        final String payload = this.getPayload(request);
        final String header = request.getHeader("Stripe-Signature");
        final Event event = Webhook.constructEvent(payload, header, this.stripeApiWebhookSecretKey);
        this.processWebhookEvent(event);

        return ResponseEntity.noContent().build();
    }

    private void processWebhookEvent(Event event) {
        switch (event.getType()) {
            case "checkout.session.completed":
                this.extractSessionFromEvent(event).ifPresent(session -> {
                    final String paymentId = session.getMetadata().get("paymentId");

                    this.messageProducer.publish(UPDATE_PAYMENT_STATUS_EXCHANGE, UPDATE_PAYMENT_STATUS_ROUTING_KEY,
                            UpdatePaymentStatusCommand.with(paymentId, PaymentStatus.APPROVED)
                    );
                });
                break;
            case "checkout.session.expired":
                this.extractSessionFromEvent(event).ifPresent(session -> {
                    final String paymentId = session.getMetadata().get("paymentId");

                    this.messageProducer.publish(UPDATE_PAYMENT_STATUS_EXCHANGE, UPDATE_PAYMENT_STATUS_ROUTING_KEY,
                            UpdatePaymentStatusCommand.with(paymentId, PaymentStatus.EXPIRED)
                    );
                });
                break;
        }
    }

    private Optional<Session> extractSessionFromEvent(Event event) {
        return event.getDataObjectDeserializer()
                .getObject()
                .filter(Session.class::isInstance)
                .map(Session.class::cast);
    }

    private String getPayload(HttpServletRequest request) throws IOException {
        return new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }
}
