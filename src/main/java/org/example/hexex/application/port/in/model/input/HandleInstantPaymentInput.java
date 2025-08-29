package org.example.hexex.application.port.in.model.input;

public record HandleInstantPaymentInput(
  String id,
  String type,
  String intentId,
  String paymentId,
  String status,
  String reconciliationStatus,
  String postInitiationStatus
) {
}
