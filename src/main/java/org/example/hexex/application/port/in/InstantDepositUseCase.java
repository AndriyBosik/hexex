package org.example.hexex.application.port.in;

import org.example.hexex.application.port.in.model.input.InitiateInstantDepositInput;
import org.example.hexex.application.port.in.model.output.DepositOutput;

public interface InstantDepositUseCase {
  DepositOutput deposit(InitiateInstantDepositInput input);
}
