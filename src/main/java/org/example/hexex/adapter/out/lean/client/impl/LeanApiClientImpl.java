package org.example.hexex.adapter.out.lean.client.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hexex.adapter.out.lean.client.LeanApiClient;
import org.example.hexex.adapter.out.lean.dto.LeanCustomerRequestDto;
import org.example.hexex.adapter.out.lean.dto.LeanCustomerResponseDto;
import org.example.hexex.adapter.out.lean.properties.LeanProperties;
import org.example.hexex.application.port.out.model.LeanTokenDto;
import org.example.hexex.domain.exception.HexexBusinessRuntimeException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeanApiClientImpl implements LeanApiClient {
  private static final String LEAN_API_SCOPE = "api";
  private static final String LEAN_OAUTH2_TOKEN = "/oauth2/token";

  private final RestTemplate restTemplate;
  private final LeanProperties properties;
  private final ObjectMapper objectMapper;

  @Override
  public LeanCustomerResponseDto createCustomer(LeanCustomerRequestDto request) {
    URI uri = getLeanUrl("/customers/v1");
    Map<String, String> body = new HashMap<>();
    body.put("app_user_id", request.appUserId());
    try {
      HttpEntity<String> entity = new HttpEntity<>(objectMapper.writeValueAsString(body), getHeader());
      ResponseEntity<LeanCustomerResponseDto> responseEntity = restTemplate.postForEntity(uri.toString(), entity, LeanCustomerResponseDto.class);
      return responseEntity.getBody();
    } catch (HttpClientErrorException.Conflict e) {
      return findCustomer(request.appUserId()).orElseThrow();
    } catch (JsonProcessingException e) {
      throw new HexexBusinessRuntimeException(e);
    }
  }

  @Override
  public Optional<LeanCustomerResponseDto> findCustomer(String appUserId) {
    URI uri = getLeanUrl("/customers/v1/app-user-id/" + appUserId);
    HttpEntity<String> request = new HttpEntity<>(null, getHeader());
    ResponseEntity<LeanCustomerResponseDto> responseEntity = restTemplate.exchange(uri.toString(), HttpMethod.GET, request, LeanCustomerResponseDto.class);
    if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.getBody() != null) {
      return Optional.of(responseEntity.getBody());
    }
    return Optional.empty();
  }

  @Override
  public LeanTokenDto getLeanCustomerToken(String customerId) {
    return getLeanOAuth2Token(String.format("customer.%s", customerId));
  }

  private HttpHeaders getHeader() {
    HttpHeaders header = new HttpHeaders();
    header.setContentType(MediaType.APPLICATION_JSON);
    header.setAccept(List.of(MediaType.ALL));
    header.add("Authorization", String.format("Bearer %s", getLeanOAuth2Token(LEAN_API_SCOPE)));
    return header;
  }

  private LeanTokenDto getLeanOAuth2Token(String scope) {
    URI uri = URI.create(properties.authUrl() + LEAN_OAUTH2_TOKEN);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setAccept(List.of(MediaType.ALL));

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "client_credentials");
    body.add("client_id", properties.clientId());
    body.add("client_secret", properties.clientSecret());
    body.add("scope", scope);

    try {
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
      ResponseEntity<LeanTokenDto> responseEntity = restTemplate.postForEntity(uri.toString(), request, LeanTokenDto.class);
      if (HttpStatus.OK.equals(responseEntity.getStatusCode()) && responseEntity.getBody() != null) {
        return responseEntity.getBody();
      }
    } catch (Exception e) {
      log.error("An error occurred when fetching Lean access token", e);
    }
    return null;
  }

  private URI getLeanUrl(String path) {
    return URI.create(properties.url() + path);
  }
}
