package org.example.hexex.application.port.out.result;

import org.example.hexex.domain.model.result.DepositResult;

public interface StoreDepositResultPort {
  DepositResult transfer(DepositResult deposit);
}
