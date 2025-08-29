package org.example.hexex.application.service;

import lombok.RequiredArgsConstructor;
import org.example.hexex.adapter.out.lean.dto.LeanCustomerResponseDto;
import org.example.hexex.application.port.in.LeanTokenUseCase;
import org.example.hexex.application.port.out.GenerateLeanTokenPort;
import org.example.hexex.application.port.out.LoadCurrentUserPort;
import org.example.hexex.application.port.out.LoadLeanCustomerPort;
import org.example.hexex.application.port.out.model.LeanTokenDto;
import org.example.hexex.common.annotation.UseCase;
import org.example.hexex.domain.model.UserIdentity;

@UseCase
@RequiredArgsConstructor
public class LeanTokenUseCaseImpl implements LeanTokenUseCase {
  private final LoadCurrentUserPort loadCurrentUserPort;
  private final LoadLeanCustomerPort loadLeanCustomerPort;
  private final GenerateLeanTokenPort generateLeanTokenPort;

  @Override
  public LeanTokenDto generate() {
    UserIdentity identity = loadCurrentUserPort.transfer();
    LeanCustomerResponseDto leanCustomer = loadLeanCustomerPort.transfer(identity.email());
    return generateLeanTokenPort.exchange(leanCustomer.customerId());
  }
}
