package org.example.hexex.adapter.in.rest.model;

import java.math.BigDecimal;

public record PaymentDetailsDto(BigDecimal amount, String currency) {
}
