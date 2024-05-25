package org.example.payment.payment.adapter.in.web.view;

import lombok.RequiredArgsConstructor;
import org.example.payment.common.IdempotencyCreator;
import org.example.payment.common.WebAdapter;
import org.example.payment.payment.adapter.in.web.request.CheckoutRequest;
import org.example.payment.payment.application.port.in.CheckoutCommand;
import org.example.payment.payment.application.port.in.CheckoutUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@WebAdapter
@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutUseCase checkoutUseCase;

    @GetMapping
    public Mono<String> checkout(CheckoutRequest request, Model model) {
        CheckoutCommand command = CheckoutCommand.builder()
                .buyerId(request.buyerId())
                .cartId(request.cartId())
                .idempotencyKey(IdempotencyCreator.create(request.seed()))
                .productIds(request.productIds())
                .build();

        return checkoutUseCase.checkout(command)
                .map(result -> {
                    model.addAttribute("orderId", result.orderId());
                    model.addAttribute("amount", result.amount());
                    model.addAttribute("orderName", result.orderName());
                    return "checkout";
                });
    }
}
