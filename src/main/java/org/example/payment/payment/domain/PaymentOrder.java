package org.example.payment.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class PaymentOrder {
    private final Long id;
    private final Long paymentEventId;
    private final Long sellerId;
    private final Long buyerId;
    private final Long productId;
    private final String orderId;
    private final BigDecimal amount;
    private final PaymentStatus paymentStatus;
    private boolean isLedgerUpdated;
    private boolean isWalletUpdated;
}
