package org.example.hexex.domain.model.result;

import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;

public record HandlePaymentChangeResult(
  Wallet wallet,
  Deposit deposit,
  Investment investment
) {
}
