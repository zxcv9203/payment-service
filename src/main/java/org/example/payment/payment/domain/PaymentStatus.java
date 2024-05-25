package org.example.payment.payment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PaymentStatus {
    NOT_STARTED("결제 시작 전"),
    EXECUTING("결제 승인 중"),
    SUCCESS ("결제 승인 성공"),
    FAILURE("결제 승인 실패"),
    UNKNOWN("결제 승인 알 수 없는 상태");

    private final String description;

    public static PaymentStatus findByName(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결제 상태를 찾을 수 없습니다. name=" + name));
    }
}
