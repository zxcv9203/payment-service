package org.example.payment.payment.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.example.payment.payment.adapter.out.persistence.exception.PaymentValidationException;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Repository
@RequiredArgsConstructor
public class R2DBCPaymentValidationRepository implements PaymentValidationRepository {
    private static final String SELECT_PAYMENT_TOTAL_AMOUNT_QUERY = "SELECT SUM(amount) as total_amount FROM payment_order WHERE order_id = :orderId";

    private final DatabaseClient databaseClient;

    @Override
    public Mono<Boolean> isValid(String orderId, Long amount) {
        return databaseClient.sql(SELECT_PAYMENT_TOTAL_AMOUNT_QUERY)
                .bind("orderId", orderId)
                .fetch()
                .first()
                .handle((row, sink) -> {
                    Long totalAmount = (Long) row.get("total_amount");
                    if (totalAmount.equals(amount)) {
                        sink.next(true);
                    } else {
                        sink.error(new PaymentValidationException(MessageFormat.format("주문 (orderId : {0})의 총 결제 금액이 {1}원이 아닙니다.", orderId, amount)));
                    }
                });
    }
}
