package org.example.hexex.configuration;

import org.apache.hc.client5.http.HttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.DefaultHttpRequestRetryStrategy;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponseInterceptor;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class Rest {
  @Bean
  public RestTemplate restTemplate(
    HttpRequestRetryStrategy httpRequestRetryStrategy,
    HttpResponseInterceptor httpResponseInterceptor
  ) {
    return new RestTemplateBuilder()
      .requestFactory(() -> new BufferingClientHttpRequestFactory(
        new HttpComponentsClientHttpRequestFactory(
          HttpClients.custom().addResponseInterceptorLast(httpResponseInterceptor
            )
            .setRetryStrategy(httpRequestRetryStrategy).build()))
      )
      .readTimeout(Duration.ofSeconds(60))
      .connectTimeout(Duration.ofSeconds(60))
      .additionalInterceptors(new LoggingRequestInterceptor())
      .build();
  }

  @Bean
  public HttpResponseInterceptor httpResponseInterceptor() {
    return (httpResponse, entity, httpContext) -> {
      if (httpResponse.getCode() == HttpURLConnection.HTTP_UNAVAILABLE) {
        throw new RuntimeException("Retry http request");
      }
    };
  }

  @Bean
  public HttpRequestRetryStrategy httpRequestRetryStrategy() {
    return new DefaultHttpRequestRetryStrategy() {
      @Override
      public boolean retryRequest(HttpRequest request, IOException exception, int executionCount, HttpContext context) {
        if (executionCount > 3) {
          return false;
        }
        try {
          TimeUnit.MILLISECONDS.sleep(executionCount * 500L);
        } catch (InterruptedException ignore) {
          Thread.currentThread().interrupt();
        }
        return true;
      }
    };
  }
}
