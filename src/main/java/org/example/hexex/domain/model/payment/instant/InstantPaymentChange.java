package org.example.hexex.domain.model.payment.instant;

public record InstantPaymentChange(
  String intentId,
  String paymentId,
  String status,
  String reconciliationStatus,
  String postInitiationStatus
) {
}
