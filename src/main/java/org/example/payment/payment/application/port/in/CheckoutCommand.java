package org.example.payment.payment.application.port.in;

import lombok.Builder;

import java.util.List;

@Builder
public record CheckoutCommand(
        Long cartId,
        List<Long> productIds,
        Long buyerId,
        String idempotencyKey
) {
}
