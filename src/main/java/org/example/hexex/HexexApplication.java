package org.example.hexex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HexexApplication {
  // TODO implement ArchUnit tests
  // TODO handle exceptions correctly on all layers
  // TODO structure DTOs correctly (what should be in application package and what should be in adapter package)
  // TODO implement wallet transaction logs
  // TODO explain that using commands in REST resources is generally a bad practice and we should use DTOs and map them to commands
  // TODO show that webhook is responsible to validate input request
  // TODO rename model package to entity
  // TODO remove unused code
  public static void main(String[] args) {
    SpringApplication.run(HexexApplication.class, args);
  }
}
