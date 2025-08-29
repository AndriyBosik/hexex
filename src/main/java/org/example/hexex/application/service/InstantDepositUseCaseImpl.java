package org.example.hexex.application.service;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.in.InstantDepositUseCase;
import org.example.hexex.application.port.in.model.input.InitiateInstantDepositInput;
import org.example.hexex.application.port.in.model.output.DepositOutput;
import org.example.hexex.application.port.out.InitiatePaymentPort;
import org.example.hexex.application.port.out.LoadCurrentUserPort;
import org.example.hexex.application.port.out.LoadInvestorPort;
import org.example.hexex.application.port.out.result.StoreDepositResultPort;
import org.example.hexex.common.annotation.UseCase;
import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investor;
import org.example.hexex.domain.model.UserIdentity;
import org.example.hexex.domain.model.payment.instant.InstantPayment;
import org.example.hexex.domain.model.payment.instant.InstantPaymentInfo;
import org.example.hexex.domain.model.result.DepositResult;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InstantDepositUseCaseImpl implements InstantDepositUseCase {
  private final LoadCurrentUserPort loadCurrentUserPort;
  private final LoadInvestorPort loadInvestorPort;
  private final StoreDepositResultPort storeDepositResultPort;
  private final InitiatePaymentPort<InstantPaymentInfo, InstantPayment> instantPaymentPort;

  @Override
  @Transactional
  public DepositOutput deposit(InitiateInstantDepositInput input) {
    UserIdentity identity = loadCurrentUserPort.transfer();
    Investor investor = loadInvestorPort.transfer(identity.email(), "AED")
      .orElseThrow();
    InstantPayment payment = instantPaymentPort.transfer(new InstantPaymentInfo(investor.email(), input.paymentSourceId(), input.amount()));
    Deposit deposit = investor.initiateDeposit();
    return DepositOutput.from(storeDepositResultPort.transfer(new DepositResult(deposit, payment)));
  }
}
