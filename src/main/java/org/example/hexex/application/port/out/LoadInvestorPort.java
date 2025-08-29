package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.Investor;

import java.util.Optional;

public interface LoadInvestorPort {
  Optional<Investor> transfer(String email, String currency);
}
