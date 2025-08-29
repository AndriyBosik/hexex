package org.example.hexex.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;
import org.example.hexex.domain.model.common.Deferred;
import org.example.hexex.domain.model.payment.instant.Payment;
import org.example.hexex.domain.service.DepositService;
import org.example.hexex.domain.service.InvestmentService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {
  private final InvestmentService investmentService;

  @Override
  public void processPayment(Payment payment, Deposit deposit, Wallet wallet, Deferred<Investment> investment) {
    if (payment.transactionStatus().isProcessing()) {
      deposit.startProcessing();
      return;
    }
    deposit.complete(payment.transactionStatus());
    if (deposit.getStatus().isSuccessful()) {
      wallet.add(payment.amount());
    }
    investment.retrieve()
      .ifPresent(inv -> investmentService.process(wallet, deposit, inv));
  }
}
