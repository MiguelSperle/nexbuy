package com.miguelsperle.nexbuy.shared.domain.events;

public record PaymentStatusUpdatedEvent(
    String orderId,
    String paymentStatus
) {
    public static PaymentStatusUpdatedEvent from(String orderId, String paymentStatus) {
        return new PaymentStatusUpdatedEvent(orderId, paymentStatus);
    }
}
