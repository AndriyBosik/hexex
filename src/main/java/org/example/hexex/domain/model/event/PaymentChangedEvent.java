package org.example.hexex.domain.model.event;

import org.example.hexex.domain.model.payment.instant.Payment;

public record PaymentChangedEvent(
  Payment payment
) implements Event {
}
