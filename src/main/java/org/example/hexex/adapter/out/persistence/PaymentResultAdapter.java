package org.example.hexex.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.hexex.adapter.out.persistence.repository.DepositRepository;
import org.example.hexex.adapter.out.persistence.repository.WalletRepository;
import org.example.hexex.adapter.out.persistence.repository.model.DepositEntity;
import org.example.hexex.adapter.out.persistence.repository.model.WalletEntity;
import org.example.hexex.application.port.out.result.StorePaymentResultPort;
import org.example.hexex.common.annotation.Adapter;
import org.example.hexex.domain.exception.HexexBusinessRuntimeException;
import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.Investment;
import org.example.hexex.domain.model.Wallet;
import org.example.hexex.domain.model.result.ProcessPaymentResult;

@Adapter
@RequiredArgsConstructor
public class PaymentResultAdapter implements StorePaymentResultPort {
  private final WalletRepository walletRepository;
  private final DepositRepository depositRepository;

  @Override
  public ProcessPaymentResult transfer(ProcessPaymentResult result) {
    if (result.deposit().getId() == null || result.wallet().getId() == null) {
      throw new HexexBusinessRuntimeException("Invalid payment result");
    }

    WalletEntity walletEntity = walletRepository.findById(result.wallet().getId())
      .orElseThrow();
    walletEntity.setAvailable(result.wallet().getAvailable());
    walletEntity.setTotal(result.wallet().getTotal());
    WalletEntity savedWalletEntity = walletRepository.save(walletEntity);

    DepositEntity depositEntity = depositRepository.findById(result.deposit().getId())
      .orElseThrow();
    depositEntity.setTransactionStatus(result.deposit().getStatus());
    DepositEntity savedDepositEntity = depositRepository.save(depositEntity);

    Investment investment = store(result.investment());

    return new ProcessPaymentResult(
      new Wallet(
        savedWalletEntity.getId(),
        savedWalletEntity.getCurrency().getCode(),
        savedWalletEntity.getAvailable(),
        savedWalletEntity.getTotal()),
      new Deposit(
        savedDepositEntity.getId(),
        savedDepositEntity.getTransactionStatus(),
        savedDepositEntity.getWallet().getId(),
        savedDepositEntity.getCreatedAt(),
        savedDepositEntity.getUpdatedAt()),
      investment);
  }

  private Investment store(Investment investment) {
    return investment;
  }
}
