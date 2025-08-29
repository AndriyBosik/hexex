package org.example.hexex.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.in.model.input.HandleInstantPaymentInput;
import org.example.hexex.application.port.in.HandleInstantPaymentUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhooks/lean")
public class LeanWebhookResource {
  private final HandleInstantPaymentUseCase useCase;

  @PostMapping
  public ResponseEntity<Void> handle(
    @RequestHeader("X-Lean-Signature") String signature,
    @RequestBody HandleInstantPaymentInput webhook
  ) {
    if (!signature.equals("asl@#dFH#ASdejalS#@DFjasdqf!@#yA")) {
      return ResponseEntity.badRequest().build();
    }
    useCase.handle(webhook);
    return ResponseEntity.ok().build();
  }
}
