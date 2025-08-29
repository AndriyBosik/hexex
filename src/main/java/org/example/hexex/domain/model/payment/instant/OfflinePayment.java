package org.example.hexex.domain.model.payment.instant;

import org.example.hexex.common.meta.TransactionStatus;

import java.math.BigDecimal;

public record OfflinePayment(
  Long id,
  BigDecimal amount,
  String currency,
  TransactionStatus transactionStatus,
  String bankName,
  String bankAccountNumber
) implements Payment {
}
