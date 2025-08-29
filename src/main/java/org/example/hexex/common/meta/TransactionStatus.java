package org.example.hexex.common.meta;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum TransactionStatus {
  INITIATED,
  PROCESSING,
  SUCCESSFUL,
  FAILED,
  REJECTED,
  CANCELED,
  EXPIRED,
  PRE_APPROVE;

  public static Set<TransactionStatus> getPendingAndRejected() {
    return Arrays.stream(TransactionStatus.values())
      .filter(value -> value.isPending() || value.isUnsuccessful())
      .collect(Collectors.toSet());
  }

  public static Set<TransactionStatus> getPendingAndCompleted() {
    return Arrays.stream(TransactionStatus.values())
      .filter(value -> value.isPending() || value.isSuccessful())
      .collect(Collectors.toSet());
  }

  public static Set<TransactionStatus> getPending() {
    return Arrays.stream(TransactionStatus.values())
      .filter(TransactionStatus::isPending)
      .collect(Collectors.toSet());
  }

  public static Set<TransactionStatus> getSuccessful() {
    return Arrays.stream(TransactionStatus.values())
      .filter(TransactionStatus::isSuccessful)
      .collect(Collectors.toSet());
  }

  public boolean isCanceled() {
    return this == CANCELED;
  }

  public boolean isUnsuccessful() {
    return this == FAILED || this == REJECTED || this == EXPIRED;
  }

  public boolean isSuccessful() {
    return this == SUCCESSFUL;
  }

  public boolean isInitiated() {
    return this == INITIATED;
  }

  public boolean isPending() {
    return this == INITIATED || this == PROCESSING;
  }

  public boolean isProcessing() {
    return this == PROCESSING;
  }

  public boolean isCompleted() {
    return !isPending();
  }

  public boolean isPreApprove() {
    return this == PRE_APPROVE;
  }
}
