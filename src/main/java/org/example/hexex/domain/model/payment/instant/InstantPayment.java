package org.example.hexex.domain.model.payment.instant;

import org.example.hexex.common.meta.TransactionStatus;
import org.example.hexex.domain.exception.HexexBusinessRuntimeException;

import java.math.BigDecimal;

public record InstantPayment(
  Long id,
  String intentId,
  String paymentId,
  String status,
  String reconciliationStatus,
  String postInitiationStatus,
  BigDecimal amount,
  String currency
) implements Payment {
  public TransactionStatus transactionStatus() {
    return switch (status) {
      case null -> TransactionStatus.INITIATED;
      case "ACCEPTED_BY_BANK" -> TransactionStatus.SUCCESSFUL;
      case "FAILED" -> TransactionStatus.FAILED;
      case "REJECTED" -> TransactionStatus.REJECTED;
      default -> TransactionStatus.PROCESSING;
    };
  }

  public InstantPayment apply(InstantPaymentChange change) {
    if (transactionStatus().isCompleted()) {
      throw new HexexBusinessRuntimeException("Cannot apply change to completed instant payment");
    }
    return new InstantPayment(
      id,
      intentId,
      paymentId,
      change.status(),
      change.reconciliationStatus(),
      change.postInitiationStatus(),
      amount,
      currency);
  }
}
