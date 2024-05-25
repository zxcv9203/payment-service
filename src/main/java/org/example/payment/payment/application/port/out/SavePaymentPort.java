package org.example.payment.payment.application.port.out;

import org.example.payment.payment.domain.PaymentEvent;
import reactor.core.publisher.Mono;

public interface SavePaymentPort {

    Mono<Void> save(PaymentEvent paymentEvent);
}
