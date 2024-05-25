package org.example.payment.payment.application.service;

import org.example.payment.payment.application.port.in.CheckoutCommand;
import org.example.payment.payment.application.port.in.CheckoutUseCase;
import org.example.payment.payment.domain.PaymentEvent;
import org.example.payment.payment.domain.PaymentOrder;
import org.example.payment.payment.helper.PaymentDatabaseHelper;
import org.example.payment.payment.helper.PaymentTestConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(PaymentTestConfiguration.class)
class CheckoutServiceTest {

    @Autowired
    private CheckoutUseCase checkoutUseCase;

    @Autowired
    private PaymentDatabaseHelper paymentDatabaseHelper;

    @Test
    @DisplayName("Payment event와 PaymentOrder가 정상적으로 저장되는지 테스트")
    void saveTest() {
        String orderId = UUID.randomUUID().toString();
        CheckoutCommand command = CheckoutCommand.builder()
                .cartId(1L)
                .buyerId(1L)
                .productIds(List.of(1L, 2L, 3L))
                .idempotencyKey(orderId)
                .build();

        StepVerifier.create(checkoutUseCase.checkout(command))
                .expectNextMatches(result ->
                        result.amount().intValue() == 60000 && Objects.equals(result.orderId(), orderId))
                .verifyComplete();

        PaymentEvent payments = paymentDatabaseHelper.getPayments(orderId);

        assertThat(payments.getOrderId()).isEqualTo(orderId);
        assertThat(payments.getPaymentOrders()).hasSize(command.productIds().size());
        assertThat(payments.getTotalAmount()).isEqualTo(60000);
        assertThat(payments.isPaymentDone()).isFalse();
        assertThat(payments.getPaymentOrders())
                .extracting(PaymentOrder::isLedgerUpdated)
                .allMatch(updated -> !updated);
        assertThat(payments.getPaymentOrders())
                .extracting(PaymentOrder::isWalletUpdated)
                .allMatch(updated -> !updated);
    }
}