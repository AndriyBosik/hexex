package org.example.hexex.domain.service;

import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;

public interface InvestmentService {
  void process(Wallet wallet, Deposit deposit, Investment investment);
}
