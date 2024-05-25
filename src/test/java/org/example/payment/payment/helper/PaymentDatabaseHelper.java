package org.example.payment.payment.helper;

import org.example.payment.payment.domain.PaymentEvent;

public interface PaymentDatabaseHelper {

    PaymentEvent getPayments(String orderId);
}
