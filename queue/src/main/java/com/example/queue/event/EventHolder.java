package com.example.queue.event;

import com.example.queue.Event;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventHolder extends ApplicationEvent {

    private final Event event;

    public EventHolder(Object source, Event event) {
        super(source);
        this.event = event;
    }
}
