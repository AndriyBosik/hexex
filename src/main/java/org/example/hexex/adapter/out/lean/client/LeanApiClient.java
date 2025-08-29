package org.example.hexex.adapter.out.lean.client;

import org.example.hexex.adapter.out.lean.dto.LeanCustomerRequestDto;
import org.example.hexex.adapter.out.lean.dto.LeanCustomerResponseDto;
import org.example.hexex.application.port.out.model.LeanTokenDto;

import java.util.Optional;

public interface LeanApiClient {
  LeanCustomerResponseDto createCustomer(LeanCustomerRequestDto request);

  Optional<LeanCustomerResponseDto> findCustomer(String appUserId);

  LeanTokenDto getLeanCustomerToken(String customerId);
}
