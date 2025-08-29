package org.example.hexex.domain.model.result;

import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;

public record ProcessInvestmentResult(
  Wallet wallet,
  Investment investment
) {
}
