package org.example.payment.payment.adapter.out.persistence.repository;

import reactor.core.publisher.Mono;

public interface PaymentValidationRepository {

    Mono<Boolean> isValid(String orderId, Long amount);
}
