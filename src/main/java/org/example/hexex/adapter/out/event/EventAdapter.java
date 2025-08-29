package org.example.hexex.adapter.out.event;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.out.EventPort;
import org.example.hexex.common.annotation.Adapter;
import org.example.hexex.domain.model.event.Event;
import org.springframework.context.ApplicationEventPublisher;

@Adapter
@RequiredArgsConstructor
public class EventAdapter implements EventPort {
  private final ApplicationEventPublisher publisher;

  @Override
  public void send(Event event) {
    publisher.publishEvent(event);
  }
}
