package org.example.payment.payment.adapter.in.web.request;

public record TossPaymentConfirmRequest(
        String paymentKey,
        String orderId,
        Long amount
) {
}
