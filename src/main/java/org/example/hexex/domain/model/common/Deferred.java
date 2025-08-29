package org.example.hexex.domain.model.common;

import java.util.Optional;
import java.util.function.Supplier;

public class Deferred<T> {
  private final Supplier<T> supplier;
  private boolean retrieved = false;
  private T value;

  public Deferred(Supplier<T> supplier) {
    this.supplier = supplier;
  }

  public Deferred(T value) {
    this.supplier = () -> value;
  }

  public Optional<T> retrieve() {
    if (!retrieved) {
      retrieved = true;
      value = supplier.get();
    }
    return Optional.ofNullable(value);
  }

  public Optional<T> peek() {
    return Optional.ofNullable(value);
  }
}
