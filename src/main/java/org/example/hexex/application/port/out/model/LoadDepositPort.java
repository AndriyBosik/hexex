package org.example.hexex.application.port.out.model;

import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.payment.instant.Payment;

import java.util.Optional;

public interface LoadDepositPort {
  Optional<Deposit> transfer(Payment payment);
}
