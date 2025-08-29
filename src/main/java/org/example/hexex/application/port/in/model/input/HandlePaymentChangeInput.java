package org.example.hexex.application.port.in.model.input;

import org.example.hexex.domain.model.payment.instant.Payment;

public record HandlePaymentChangeInput(Payment payment) {
}
