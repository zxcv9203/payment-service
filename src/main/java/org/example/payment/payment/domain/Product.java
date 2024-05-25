package org.example.payment.payment.domain;

import java.math.BigDecimal;

public record Product(
        Long id,
        BigDecimal amount,
        int quantity,
        String name,
        Long sellerId
) {
}
