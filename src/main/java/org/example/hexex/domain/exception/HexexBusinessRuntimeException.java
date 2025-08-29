package org.example.hexex.domain.exception;

public class HexexBusinessRuntimeException extends RuntimeException {
  public HexexBusinessRuntimeException(String message) {
    super(message);
  }

  public HexexBusinessRuntimeException(Throwable cause) {
    super(cause);
  }
}
