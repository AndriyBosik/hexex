package org.example.hexex.adapter.in.event;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.in.HandlePaymentChangeUseCase;
import org.example.hexex.application.port.in.model.input.HandlePaymentChangeInput;
import org.example.hexex.domain.model.event.PaymentChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
  private final HandlePaymentChangeUseCase useCase;

  @EventListener
  public void handlePaymentChange(PaymentChangedEvent event) {
    useCase.handle(new HandlePaymentChangeInput(event.payment()));
  }
}
