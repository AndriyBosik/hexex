package org.example.hexex.application.port.in.model.input;

import java.math.BigDecimal;

public record InitiateInstantDepositInput(
  String paymentSourceId,
  BigDecimal amount
) {
}
