package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.payment.instant.Payment;

public interface StorePaymentPort<T extends Payment> {
  T transfer(T payment);
}
