package org.example.payment.payment.adapter.out.persistence.repository;

import org.example.payment.payment.domain.PaymentEvent;
import reactor.core.publisher.Mono;

public interface PaymentRepository {

    Mono<Void> save(PaymentEvent paymentEvent);
}
