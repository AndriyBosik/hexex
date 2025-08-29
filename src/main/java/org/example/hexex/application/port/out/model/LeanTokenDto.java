package org.example.hexex.application.port.out.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LeanTokenDto(
  @JsonProperty("access_token") String accessToken,
  @JsonProperty("scope") String scope,
  @JsonProperty("token_type") String tokenType,
  @JsonProperty("expires_in") long expiresIn
) {
}
