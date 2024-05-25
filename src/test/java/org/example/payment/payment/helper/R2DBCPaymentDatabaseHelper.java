package org.example.payment.payment.helper;

import org.example.payment.payment.domain.*;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.reactive.TransactionalOperator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class R2DBCPaymentDatabaseHelper implements PaymentDatabaseHelper {

    private static final String SELECT_PAYMENT_QUERY = "SELECT * FROM payment_event pe " +
            "INNER JOIN payment_order po ON pe.order_id = po.order_id " +
            "WHERE pe.order_id = :orderId";

    private final DatabaseClient databaseClient;
    private final TransactionalOperator transactionalOperator;

    public R2DBCPaymentDatabaseHelper(DatabaseClient databaseClient, TransactionalOperator transactionalOperator) {
        this.databaseClient = databaseClient;
        this.transactionalOperator = transactionalOperator;
    }

    public PaymentEvent getPayments(String orderId) {
        return databaseClient.sql(SELECT_PAYMENT_QUERY)
                .bind("orderId", orderId)
                .fetch()
                .all()
                .groupBy(row -> ((Number) row.get("payment_event_id")).longValue())
                .flatMap(groupedFlux -> groupedFlux.collectList().map(results -> mapToPaymentEvent(groupedFlux.key(), results)))
                .next()
                .block();
    }

    private PaymentEvent mapToPaymentEvent(Long paymentEventId, List<Map<String, Object>> results) {
        Map<String, Object> firstResult = results.get(0);
        return PaymentEvent.builder()
                .id(paymentEventId)
                .orderId((String) firstResult.get("order_id"))
                .orderName((String) firstResult.get("order_name"))
                .buyerId(((Number) firstResult.get("buyer_id")).longValue())
                .paymentKey((String) firstResult.get("payment_key"))
                .paymentType(firstResult.get("type") != null ? PaymentType.valueOf((String) firstResult.get("type")) : null)
                .paymentMethod(firstResult.get("method") != null ? PaymentMethod.valueOf((String) firstResult.get("method")) : null)
                .approvedAt(firstResult.get("approved_at") != null ? ((LocalDateTime) firstResult.get("approved_at")) : null)
                .isPaymentDone(((Number) firstResult.get("is_payment_done")).intValue() == 1)
                .paymentOrders(results.stream().map(result -> PaymentOrder.builder()
                                .id(((Number) result.get("id")).longValue())
                                .paymentEventId(paymentEventId)
                                .sellerId(((Number) result.get("seller_id")).longValue())
                                .orderId((String) result.get("order_id"))
                                .productId(((Number) result.get("product_id")).longValue())
                                .amount((BigDecimal) result.get("amount"))
                                .paymentStatus(PaymentStatus.findByName((String) result.get("payment_order_state")))
                                .isLedgerUpdated(((Number) result.get("ledger_updated")).intValue() == 1)
                                .isWalletUpdated(((Number) result.get("wallet_updated")).intValue() == 1)
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
