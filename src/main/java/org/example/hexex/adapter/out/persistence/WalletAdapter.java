package org.example.hexex.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.hexex.adapter.out.persistence.repository.WalletRepository;
import org.example.hexex.application.port.out.LoadWalletPort;
import org.example.hexex.common.annotation.Adapter;
import org.example.hexex.domain.model.Wallet;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class WalletAdapter implements LoadWalletPort {
  private final WalletRepository walletRepository;

  @Override
  public Optional<Wallet> transfer(long id) {
    return walletRepository.findById(id)
      .map(entity -> new Wallet(
        entity.getId(),
        entity.getCurrency().getCode(),
        entity.getAvailable(),
        entity.getTotal()));
  }
}
