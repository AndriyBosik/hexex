package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.UserIdentity;

public interface LoadCurrentUserPort {
  UserIdentity transfer();
}
