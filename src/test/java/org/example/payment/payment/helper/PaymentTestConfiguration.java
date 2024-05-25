package org.example.payment.payment.helper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.reactive.TransactionalOperator;

@TestConfiguration
public class PaymentTestConfiguration {

    @Bean
    public PaymentDatabaseHelper paymentDatabaseHelper(DatabaseClient databaseClient, TransactionalOperator transactionalOperator) {
        return new R2DBCPaymentDatabaseHelper(databaseClient, transactionalOperator);
    }
}
