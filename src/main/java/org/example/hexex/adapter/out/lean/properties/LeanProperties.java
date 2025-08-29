package org.example.hexex.adapter.out.lean.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "lean.payment.api")
public record LeanProperties(
  String clientId,
  String clientSecret,
  String webhookSecret,
  String payoutSourceId,
  Set<String> tppCustomerIds,
  String url,
  String authUrl
) {
}
