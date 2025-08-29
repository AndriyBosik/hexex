package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.payment.instant.InstantPayment;

import java.util.Optional;

public interface LoadInstantPaymentPort {
  Optional<InstantPayment> transfer(String intentId);
}
