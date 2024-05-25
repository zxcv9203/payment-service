package org.example.payment.payment.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class PaymentEvent {
    private final Long id;
    private final Long buyerId;
    private final String orderName;
    private final String orderId;
    private final String paymentKey;
    private final PaymentType paymentType;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime approvedAt;
    private final List<PaymentOrder> paymentOrders;
    private boolean isPaymentDone;

    public Long getTotalAmount() {
        return paymentOrders.stream()
                .map(PaymentOrder::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .longValue();
    }

    public boolean isPaymentDone() {
        return isPaymentDone;
    }
}
