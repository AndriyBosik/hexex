package org.example.hexex.domain.model;

import java.time.ZonedDateTime;

public record Investor(
  Long id,
  String email,
  String firstName,
  String lastName,
  Wallet wallet,
  ZonedDateTime createdAt,
  ZonedDateTime updatedAt
) {
  public Deposit initiateDeposit() {
    return new Deposit(
      null,
      false,
      wallet.id(),
      ZonedDateTime.now(),
      ZonedDateTime.now());
  }
}
