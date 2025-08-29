package org.example.hexex.application.port.out;

import org.example.hexex.adapter.out.lean.dto.LeanCustomerResponseDto;

public interface LoadLeanCustomerPort {
  LeanCustomerResponseDto transfer(String appUserId);
}
