package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.Deposit;

public interface StoreDepositPort {
  Deposit transfer(Deposit deposit);
}
