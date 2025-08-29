package org.example.hexex.application.port.in.model.output;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.hexex.common.meta.TransactionStatus;
import org.example.hexex.domain.model.result.DepositResult;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class DepositOutput {
  private final Long id;
  private final boolean completed;
  private final TransactionStatus status;
  private final BigDecimal amount;
  private final String currency;

  public static DepositOutput from(DepositResult result) {
    return new DepositOutput(
      result.deposit().id(),
      result.deposit().completed(),
      result.payment().transactionStatus(),
      result.payment().amount(),
      result.payment().currency());
  }
}
