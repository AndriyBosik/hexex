package org.example.hexex.domain.model.result;

import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.payment.instant.Payment;

public record DepositResult(
  Deposit deposit,
  Payment payment
) {
}
