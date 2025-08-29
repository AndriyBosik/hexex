package org.example.hexex.domain.model.payment.instant;

import java.math.BigDecimal;

public record InstantPaymentInfo(String email, String paymentSourceId, BigDecimal amount) {
}
