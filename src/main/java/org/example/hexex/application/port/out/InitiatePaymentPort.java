package org.example.hexex.application.port.out;

public interface InitiatePaymentPort<T, R> {
  // TODO find better name (e. g. exchange)
  R transfer(T request);
}
