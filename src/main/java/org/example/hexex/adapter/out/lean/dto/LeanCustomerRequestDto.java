package org.example.hexex.adapter.out.lean.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LeanCustomerRequestDto(@JsonProperty("app_user_id") String appUserId) {
}
