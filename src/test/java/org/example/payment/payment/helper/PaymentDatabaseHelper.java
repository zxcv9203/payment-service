package org.example.payment.payment.helper;

import org.example.payment.payment.domain.PaymentEvent;
import reactor.core.publisher.Mono;

public interface PaymentDatabaseHelper {

    PaymentEvent getPayments(String orderId);

    Mono<Void> clear();
}
