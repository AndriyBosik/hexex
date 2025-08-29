package org.example.hexex.domain.model.payment.instant;

import org.example.hexex.common.meta.TransactionStatus;

import java.math.BigDecimal;

public sealed interface Payment permits InstantPayment, OfflinePayment {
  Long id();

  TransactionStatus transactionStatus();

  BigDecimal amount();

  String currency();
}
