package com.example.students.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventSaveStudentListener {

    @EventListener
    public void listen(EventSaveStudent eventSaveStudent){
        System.out.println("Student created: " + eventSaveStudent.getStudent().toString());
    }

}
