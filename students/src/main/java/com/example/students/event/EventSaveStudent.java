package com.example.students.event;

import com.example.students.model.Student;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class EventSaveStudent extends ApplicationEvent {

    private final Student student;
    public EventSaveStudent(Object source, Student student) {
        super(source);
        this.student = student;
    }


}
