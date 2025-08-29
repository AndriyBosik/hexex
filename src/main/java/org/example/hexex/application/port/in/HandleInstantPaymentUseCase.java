package org.example.hexex.application.port.in;

import org.example.hexex.application.port.in.model.input.HandleInstantPaymentInput;

public interface HandleInstantPaymentUseCase {
  void handle(HandleInstantPaymentInput input);
}
