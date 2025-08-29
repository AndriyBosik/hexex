package org.example.hexex.application.port.in;

import org.example.hexex.application.port.in.model.input.HandlePaymentChangeInput;

public interface HandlePaymentChangeUseCase {
  void handle(HandlePaymentChangeInput command);
}
