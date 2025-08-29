package org.example.hexex.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexex.common.meta.InvestmentStatus;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Investment {
  private final Long id;
  private final long propertyId;
  private final long investorId;
  private final BigDecimal walletAmount;
  private final BigDecimal bankAmount;
  private InvestmentStatus status;

  public void approve() {
    status = InvestmentStatus.APPROVED;
  }

  public void reject() {
    status = InvestmentStatus.REJECTED;
  }

  public void cancel() {
    status = InvestmentStatus.CANCELLED;
  }
}
