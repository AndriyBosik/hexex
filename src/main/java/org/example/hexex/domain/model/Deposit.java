package org.example.hexex.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexex.common.meta.TransactionStatus;
import org.example.hexex.domain.exception.HexexBusinessRuntimeException;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class Deposit {
  private final Long id;
  private TransactionStatus status;
  private final long walletId;
  private final ZonedDateTime createdAt;
  private final ZonedDateTime updatedAt;

  public void complete(TransactionStatus status) {
    if (completed()) {
      throw new HexexBusinessRuntimeException("Payment already applied");
    }
    this.status = status;
  }

  public void startProcessing() {
    if (completed()) {
      throw new HexexBusinessRuntimeException("Deposit already completed");
    }
    status = TransactionStatus.PROCESSING;
  }

  private boolean completed() {
    return status.isCompleted();
  }
}
