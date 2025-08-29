package org.example.hexex.application.port.out;

import org.example.hexex.application.port.out.model.LeanTokenDto;

public interface GenerateLeanTokenPort {
  LeanTokenDto exchange(String customerId);
}
