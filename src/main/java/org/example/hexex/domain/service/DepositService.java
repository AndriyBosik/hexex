package org.example.hexex.domain.service;

import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;
import org.example.hexex.domain.model.common.Deferred;
import org.example.hexex.domain.model.payment.instant.Payment;

public interface DepositService {
  void processPayment(Payment payment, Deposit deposit, Wallet wallet, Deferred<Investment> investment);
}
