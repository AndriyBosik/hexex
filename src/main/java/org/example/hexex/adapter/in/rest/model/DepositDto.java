package org.example.hexex.adapter.in.rest.model;

import org.example.hexex.common.meta.TransactionStatus;

import java.time.ZonedDateTime;

public record DepositDto(
  Long id,
  TransactionStatus status,
  PaymentDetailsDto details,
  WalletDto wallet,
  ZonedDateTime createdAt,
  ZonedDateTime updatedAt
) {
}
