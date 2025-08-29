package org.example.hexex.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexex.domain.exception.HexexBusinessRuntimeException;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Wallet {
  private final Long id;
  private final String currency;
  private BigDecimal available;
  private BigDecimal total;

  public boolean hasSufficientFunds(BigDecimal amount) {
    return available.compareTo(amount) >= 0;
  }

  public void add(BigDecimal amount) {
    total = total.add(amount);
    available = available.add(amount);
  }

  public BigDecimal lock(BigDecimal amount) {
    available = available.subtract(amount);
    return available;
  }

  public BigDecimal capture(BigDecimal amount) {
    total = total.subtract(amount);
    if (total.compareTo(available) < 0) {
      throw new HexexBusinessRuntimeException("Total wallet balance is less than available");
    }
    return total;
  }

  public BigDecimal unlock(BigDecimal amount) {
    available = available.add(amount);
    if (available.compareTo(total) > 0) {
      throw new HexexBusinessRuntimeException("Available wallet balance is greater than total");
    }
    return available;
  }
}
