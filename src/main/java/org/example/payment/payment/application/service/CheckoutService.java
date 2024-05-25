package org.example.payment.payment.application.service;

import lombok.RequiredArgsConstructor;
import org.example.payment.common.UseCase;
import org.example.payment.payment.application.port.in.CheckoutCommand;
import org.example.payment.payment.application.port.in.CheckoutUseCase;
import org.example.payment.payment.application.port.out.LoadProductPort;
import org.example.payment.payment.application.port.out.SavePaymentPort;
import org.example.payment.payment.domain.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
public class CheckoutService implements CheckoutUseCase {

    private final LoadProductPort loadProductPort;
    private final SavePaymentPort savePaymentPort;

    @Override
    public Mono<CheckoutResult> checkout(CheckoutCommand command) {
        return loadProductPort.getProduct(command.cartId(), command.productIds()).collectList()
                .map(product -> createPaymentEvent(command, product))
                .flatMap(event -> savePaymentPort.save(event).thenReturn(event))
                .map(event -> CheckoutResult.builder()
                        .orderId(event.getOrderId())
                        .orderName(event.getOrderName())
                        .amount(event.getTotalAmount())
                        .build());
    }

    private PaymentEvent createPaymentEvent(CheckoutCommand command, List<Product> products) {
        return PaymentEvent.builder()
                .buyerId(command.buyerId())
                .orderId(command.idempotencyKey())
                .orderName(products.stream().map(Product::name).collect(Collectors.joining(", ")))
                .paymentOrders(products.stream().map(product -> PaymentOrder.builder()
                                .productId(product.id())
                                .amount(product.amount())
                                .sellerId(product.sellerId())
                                .orderId(command.idempotencyKey())
                                .paymentStatus(PaymentStatus.NOT_STARTED)
                                .isLedgerUpdated(false)
                                .isWalletUpdated(false)
                                .build())
                        .toList())
                .build();
    }

}
