package org.example.payment.payment.domain;

import lombok.Builder;

@Builder
public record CheckoutResult(
        Long amount,
        String orderId,
        String orderName
) {
}
