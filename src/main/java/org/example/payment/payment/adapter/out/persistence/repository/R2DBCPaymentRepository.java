package org.example.payment.payment.adapter.out.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.example.payment.payment.domain.PaymentEvent;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class R2DBCPaymentRepository implements PaymentRepository {

    private static final String INSERT_PAYMENT_EVENT_QUERY = """
            INSERT INTO payment_event (buyer_id, order_name, order_id)
            VALUES (:buyerId, :orderName, :orderId)
            """.trim();
    private static final String LAST_INSERT_ID_QUERY = """
            SELECT LAST_INSERT_ID()
            """.trim();

    private static final Function<String, String> INSERT_PAYMENT_ORDER_QUERY = valueClauses -> String.format(
            "INSERT INTO payment_order (payment_event_id, seller_id, order_id, product_id, amount, payment_order_state) " +
                    "VALUES %s", valueClauses);

    private final DatabaseClient databaseClient;
    private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<Void> save(PaymentEvent paymentEvent) {
        return insertPaymentEvent(paymentEvent)
                .flatMap(id -> selectPaymentEventId())
                .flatMap(paymentEventId -> insertPaymentOrder(paymentEventId, paymentEvent))
                .as(transactionalOperator::transactional)
                .then();
    }

    private Mono<Long> insertPaymentOrder(Long paymentEventId, PaymentEvent paymentEvent) {
        String valueClauses = paymentEvent.getPaymentOrders().stream()
                .map(paymentOrder -> String.format(
                        "(%d, %d, '%s', %d, %s, '%s')",
                        paymentEventId,
                        paymentOrder.getSellerId(),
                        paymentOrder.getOrderId(),
                        paymentOrder.getProductId(),
                        paymentOrder.getAmount(),
                        paymentOrder.getPaymentStatus()
                ))
                .collect(Collectors.joining(", "));
        return databaseClient.sql(INSERT_PAYMENT_ORDER_QUERY.apply(valueClauses))
                .fetch()
                .rowsUpdated();
    }

    private Mono<Long> selectPaymentEventId() {
        return databaseClient.sql(LAST_INSERT_ID_QUERY)
                .fetch()
                .first()
                .map(row -> ((BigInteger) row.get("LAST_INSERT_ID()")).longValue());
    }

    private Mono<Long> insertPaymentEvent(PaymentEvent paymentEvent) {
        return databaseClient.sql(INSERT_PAYMENT_EVENT_QUERY)
                .bind("buyerId", paymentEvent.getBuyerId())
                .bind("orderName", paymentEvent.getOrderName())
                .bind("orderId", paymentEvent.getOrderId())
                .fetch()
                .rowsUpdated();
    }
}
