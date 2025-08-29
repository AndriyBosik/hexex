package org.example.hexex.application.service;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.in.HandlePaymentChangeUseCase;
import org.example.hexex.application.port.in.model.input.HandlePaymentChangeInput;
import org.example.hexex.application.port.out.LoadInvestmentPort;
import org.example.hexex.application.port.out.LoadWalletPort;
import org.example.hexex.application.port.out.StoreInvestmentPort;
import org.example.hexex.application.port.out.model.LoadDepositPort;
import org.example.hexex.application.port.out.result.StorePaymentResultPort;
import org.example.hexex.common.annotation.UseCase;
import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;
import org.example.hexex.domain.model.common.Deferred;
import org.example.hexex.domain.model.result.ProcessPaymentResult;
import org.example.hexex.domain.service.DepositService;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HandlePaymentChangeUseCaseImpl implements HandlePaymentChangeUseCase {
  private final LoadDepositPort loadDepositPort;
  private final DepositService depositService;
  private final LoadWalletPort loadWalletPort;
  private final LoadInvestmentPort loadInvestmentPort;
  private final StorePaymentResultPort storePaymentResultPort;

  @Override
  @Transactional
  public void handle(HandlePaymentChangeInput command) {
    Deposit deposit = loadDepositPort.transfer(command.payment())
      .orElseThrow();
    Wallet wallet = loadWalletPort.transfer(deposit.getWalletId())
      .orElseThrow();
    Deferred<Investment> investment = new Deferred<>(() -> loadInvestmentPort.transfer(deposit.getId()).orElse(null));
    depositService.processPayment(command.payment(), deposit, wallet, investment);
    storePaymentResultPort.transfer(new ProcessPaymentResult(wallet, deposit, investment.peek().orElse(null)));
  }
}
