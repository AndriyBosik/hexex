package org.example.hexex.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.hexex.adapter.out.persistence.repository.WalletRepository;
import org.example.hexex.domain.model.Investor;
import org.example.hexex.domain.model.Wallet;
import org.example.hexex.application.port.out.LoadInvestorPort;
import org.example.hexex.common.annotation.Adapter;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class InvestorAdapter implements LoadInvestorPort {
  private final WalletRepository walletRepository;

  @Override
  public Optional<Investor> transfer(String email, String currency) {
    return walletRepository.findByUserEmailAndCurrencyCode(email, currency)
      .map(wallet -> new Investor(
        wallet.getUser().getId(),
        wallet.getUser().getEmail(),
        wallet.getUser().getFirstName(),
        wallet.getUser().getLastName(),
        new Wallet(
          wallet.getId(),
          wallet.getCurrency().getCode(),
          wallet.getAvailable(),
          wallet.getTotal()),
        wallet.getUser().getCreatedAt(),
        wallet.getUser().getUpdatedAt()));
  }
}
