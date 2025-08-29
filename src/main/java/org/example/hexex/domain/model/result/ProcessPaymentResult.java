package org.example.hexex.domain.model.result;

import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;

public record ProcessPaymentResult(
  Wallet wallet,
  Deposit deposit,
  Investment investment
) {
  public static ProcessPaymentResult of(Wallet wallet, Deposit deposit) {
    return new ProcessPaymentResult(wallet, deposit, null);
  }

  public ProcessPaymentResult merge(ProcessInvestmentResult investmentResult) {
    return new ProcessPaymentResult(
      investmentResult == null ? wallet : investmentResult.wallet(),
      deposit,
      investmentResult == null ? null : investmentResult.investment());
  }
}
