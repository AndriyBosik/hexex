package org.example.hexex.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.in.InstantDepositUseCase;
import org.example.hexex.application.port.in.model.input.InitiateInstantDepositInput;
import org.example.hexex.application.port.in.model.output.DepositOutput;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deposit")
public class InitiateDepositResource {
  private final InstantDepositUseCase useCase;

  @PostMapping(
    value = "/instant",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<DepositOutput> initiate(
    @RequestBody InitiateInstantDepositInput input
  ) {
    DepositOutput output = useCase.deposit(input);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(output);
  }
}
