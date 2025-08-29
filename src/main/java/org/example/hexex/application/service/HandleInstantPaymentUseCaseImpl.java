package org.example.hexex.application.service;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.in.model.input.HandleInstantPaymentInput;
import org.example.hexex.application.port.in.HandleInstantPaymentUseCase;
import org.example.hexex.application.port.out.LoadInstantPaymentPort;
import org.example.hexex.application.port.out.EventPort;
import org.example.hexex.application.port.out.StorePaymentPort;
import org.example.hexex.common.annotation.UseCase;
import org.example.hexex.domain.model.event.PaymentChangedEvent;
import org.example.hexex.domain.model.payment.instant.InstantPayment;
import org.example.hexex.domain.model.payment.instant.InstantPaymentChange;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HandleInstantPaymentUseCaseImpl implements HandleInstantPaymentUseCase {
  private final EventPort eventPort;
  private final LoadInstantPaymentPort loadInstantPaymentPort;
  private final StorePaymentPort<InstantPayment> storePaymentPort;

  @Override
  @Transactional
  public void handle(HandleInstantPaymentInput input) {
    InstantPayment payment = loadInstantPaymentPort.transfer(input.paymentId())
      .orElseThrow();
    InstantPayment changedPayment = payment.apply(new InstantPaymentChange(
      input.intentId(),
      input.paymentId(),
      input.status(),
      input.reconciliationStatus(),
      input.postInitiationStatus()));
    InstantPayment storedPayment = storePaymentPort.transfer(changedPayment);
    eventPort.send(new PaymentChangedEvent(storedPayment));
  }
}
