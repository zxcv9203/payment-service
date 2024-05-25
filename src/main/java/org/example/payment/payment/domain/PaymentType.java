package org.example.payment.payment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentType {
    NORMAL("일반 결제");

    private final String type;
}
