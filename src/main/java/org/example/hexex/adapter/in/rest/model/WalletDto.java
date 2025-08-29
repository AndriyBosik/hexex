package org.example.hexex.adapter.in.rest.model;

import java.math.BigDecimal;

public record WalletDto(Long id, String currency, BigDecimal available, BigDecimal total) {
}
