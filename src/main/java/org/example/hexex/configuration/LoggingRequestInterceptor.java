package org.example.hexex.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
  private static final int MAX_BODY_LENGTH_TO_LOG = 5000;

  @Override
  public ClientHttpResponse intercept(
    HttpRequest request,
    byte[] body,
    ClientHttpRequestExecution execution
  ) throws IOException {
    traceRequest(request, body);
    ClientHttpResponse response = execution.execute(request, body);
    traceResponse(request.getURI().getHost(), response);
    return response;
  }

  private void traceRequest(HttpRequest request, byte[] body) {
    String requestBody = compact(new String(body, StandardCharsets.UTF_8), MAX_BODY_LENGTH_TO_LOG);
    log.info("===========================request begin================================================");
    log.info("URI         : {}", request.getURI());
    log.info("Method      : {}", request.getMethod());
    log.info("Headers     : {}", request.getHeaders());
    log.info("Request body: {}", requestBody);
    log.info("===========================request end================================================");
  }

  private void traceResponse(String host, ClientHttpResponse response) throws IOException {
    String responseBody = compact(getBody(response), MAX_BODY_LENGTH_TO_LOG);
    log.info("============================response begin==========================================");
    log.info("Status code  : {}", response.getStatusCode().value());
    log.info("Status text  : {}", response.getStatusText());
    log.info("Headers      : {}", response.getHeaders());
    log.info("Response body: {}", responseBody);
    log.info("=============================response end=================================================");
  }

  private String getBody(ClientHttpResponse response) {
    try {
      StringBuilder inputStringBuilder = new StringBuilder();
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
      String line = bufferedReader.readLine();
      while (line != null) {
        inputStringBuilder.append(line);
        inputStringBuilder.append('\n');
        line = bufferedReader.readLine();
      }
      return inputStringBuilder.toString();
    } catch (Exception e) {
      log.warn("Could not get body from response");
      return "";
    }
  }

  private String compact(String text, int maxSize) {
    if (text == null) {
      return null;
    }

    String shortenedText = text;
    if (text.length() > maxSize) {
      shortenedText = text.substring(0, maxSize);
      shortenedText = String.format("%s ... [%d total symbols]", shortenedText, text.length());
    }
    return shortenedText;
  }
}
