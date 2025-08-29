package org.example.hexex.common.meta;

public enum InvestmentStatus {
  PENDING,
  APPROVED,
  REJECTED,
  CANCELLED;

  public boolean isCompleted() {
    return this != PENDING;
  }
}
