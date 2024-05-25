package org.example.payment.payment.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.payment.common.PersistenceAdapter;
import org.example.payment.payment.adapter.out.persistence.repository.PaymentRepository;
import org.example.payment.payment.application.port.out.SavePaymentPort;
import org.example.payment.payment.domain.PaymentEvent;
import reactor.core.publisher.Mono;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements SavePaymentPort {

    private final PaymentRepository paymentRepository;

    @Override
    public Mono<Void> save(PaymentEvent paymentEvent) {
        return paymentRepository.save(paymentEvent);
    }
}
