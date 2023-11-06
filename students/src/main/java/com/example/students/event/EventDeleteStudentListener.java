package com.example.students.event;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventDeleteStudentListener {

    @EventListener
    public void listen(@NotNull EventDeleteStudent eventDeleteStudent){
        System.out.println("Student with id=" + eventDeleteStudent.getId() +" deleted");
    }

}
