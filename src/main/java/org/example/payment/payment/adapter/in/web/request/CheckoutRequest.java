package org.example.payment.payment.adapter.in.web.request;

import java.time.LocalDate;
import java.util.List;

public record CheckoutRequest(
        Long cartId,
        List<Long> productIds,
        Long buyerId,
        String seed
) {
    public CheckoutRequest {
        cartId = 1L;
        productIds = List.of(1L, 2L);
        buyerId = 1L;
        seed = LocalDate.now().toString();
    }
}
