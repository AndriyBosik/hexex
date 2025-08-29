package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.Investment;

import java.util.Optional;

public interface LoadInvestmentPort {
  Optional<Investment> transfer(long depositId);
}
