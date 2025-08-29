package org.example.hexex.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;
import org.example.hexex.domain.model.result.ProcessInvestmentResult;
import org.example.hexex.domain.service.InvestmentService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestmentServiceImpl implements InvestmentService {
  @Override
  public void process(Wallet wallet, Deposit deposit, Investment investment) {
    if (deposit.getStatus().isProcessing()) {
      return;
    }
    if (deposit.getStatus().isUnsuccessful()) {
      wallet.unlock(investment.getWalletAmount());
      investment.reject();
      return;
    }
    wallet.capture(investment.getBankAmount());
    investment.approve();
  }
}
