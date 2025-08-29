package org.example.hexex.application.port.out;

import org.example.hexex.domain.model.event.Event;

public interface EventPort {
  void send(Event event);
}
