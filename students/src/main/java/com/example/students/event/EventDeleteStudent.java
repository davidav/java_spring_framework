package com.example.students.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class EventDeleteStudent extends ApplicationEvent {

    private final int id;
    public EventDeleteStudent(Object source, int id) {
        super(source);
        this.id = id;
    }
}
