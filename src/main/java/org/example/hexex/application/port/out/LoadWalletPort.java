package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.Wallet;

import java.util.Optional;

public interface LoadWalletPort {
  Optional<Wallet> transfer(long id);
}
