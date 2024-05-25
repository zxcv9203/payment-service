package org.example.payment.payment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    EASY_PAYMENT("간편 결제");

    private final String description;
}
