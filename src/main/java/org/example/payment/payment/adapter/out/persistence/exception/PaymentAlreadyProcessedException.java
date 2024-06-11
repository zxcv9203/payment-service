package org.example.payment.payment.adapter.out.persistence.exception;

import org.example.payment.payment.domain.PaymentStatus;

public class PaymentAlreadyProcessedException extends RuntimeException {
    private final PaymentStatus paymentStatus;
    private final String message;

    public PaymentAlreadyProcessedException(String message, PaymentStatus paymentStatus) {
        super(message);
        this.paymentStatus = paymentStatus;
        this.message = message;
    }
}
