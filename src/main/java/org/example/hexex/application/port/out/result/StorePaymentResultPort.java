package org.example.hexex.application.port.out.result;

import org.example.hexex.domain.model.result.ProcessPaymentResult;

public interface StorePaymentResultPort {
  ProcessPaymentResult transfer(ProcessPaymentResult result);
}
