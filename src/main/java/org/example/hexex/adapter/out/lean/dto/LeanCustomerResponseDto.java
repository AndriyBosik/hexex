package org.example.hexex.adapter.out.lean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LeanCustomerResponseDto(
  @JsonProperty("app_user_id") String appUserId,
  @JsonProperty("customer_id") String customerId
) {
}
