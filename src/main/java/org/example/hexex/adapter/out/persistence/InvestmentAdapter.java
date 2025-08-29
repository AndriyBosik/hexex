package org.example.hexex.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.out.LoadInvestmentPort;
import org.example.hexex.common.annotation.Adapter;
import org.example.hexex.domain.model.Investment;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class InvestmentAdapter implements LoadInvestmentPort {
  @Override
  public Optional<Investment> transfer(long depositId) {
    return Optional.empty();
  }
}
