package org.example.payment.payment.adapter.out.web.toss.response;

import java.util.List;

public record TossPaymentConfirmationResponse(
        String version,
        String paymentKey,
        String type,
        String orderId,
        String orderName,
        String mId,
        String currency,
        String method,
        int totalAmount,
        int balanceAmount,
        String status,
        String requestedAt,
        String approvedAt,
        Boolean useEscrow,
        String lastTransactionKey,
        int suppliedAmount,
        int vat,
        boolean cultureExpense,
        int taxFreeAmount,
        int taxExemptionAmount,
        List<Cancel> cancels,
        Card card,
        VirtualAccount virtualAccount,
        MobilePhone mobilePhone,
        GiftCertificate giftCertificate,
        Transfer transfer,
        Receipt receipt,
        Checkout checkout,
        EasyPay easyPay,
        String country,
        Failure failure,
        CashReceipt cashReceipt,
        List<CashReceipt> cashReceipts,
        Discount discount
) {
    record Cancel(
            int cancelAmount,
            String cancelReason,
            int taxFreeAmount,
            int taxExemptionAmount,
            int refundableAmount,
            int easyPayDiscountAmount,
            String canceledAt,
            String transactionKey,
            String receiptKey,
            boolean isPartialCancelable
    ) {
    }

    record Card(
            int amount,
            String issuerCode,
            String acquirerCode,
            String number,
            int installmentPlanMonths,
            String approveNo,
            boolean useCardPoint,
            String cardType,
            String ownerType,
            String acquireStatus,
            boolean isInterestFree,
            String interestPlayer
    ) {

    }

    record VirtualAccount(
            String accountType,
            String accountNumber,
            String bankCode,
            String customerName,
            String dueDate,
            String refundStatus,
            boolean expired,
            String settlementStatus,
            RefundReceiveAccount refundReceiveAccount,
            String secret
    ) {
    }

    record MobilePhone(
            String customerMobilePhone,
            String settlementStatus,
            String receiptUrl
    ) {
    }

    record GiftCertificate(
            String approveNo,
            String settlementStatus
    ) {
    }

    record Transfer(
            String bankCode,
            String settlementStatus
    ) {
    }

    record Receipt(
            String url
    ) {
    }

    record Checkout(
            String url
    ) {

    }

    record EasyPay(
            String provider,
            int amount,
            int discountAmount
    ) {}

    record Failure(
            String code,
            String message
    ) {}

    record CashReceipt(
            String type,
            String receiptKey,
            String issueNumber,
            String receiptUrl,
            int amount,
            int taxFreeAmount
    ) {}

    record Discount(
            int amount
    ) {

    }
    record RefundReceiveAccount(
            String bankCode,
            String accountNumber,
            String holderName
    ) {
    }
}

