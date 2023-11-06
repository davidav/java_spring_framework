package com.example.students.service;

import com.example.students.db.Storage;
import com.example.students.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class StudentInitialization {

    private final Storage storage;

    @EventListener(ApplicationStartedEvent.class)
    public void startInit() {
        int count = 5 + (int) (Math.random() * 10);
        saveStudents(count);
    }

    private void saveStudents(int count) {
        for (int i = 1; i < count + 1; i++) {
            storage.save(Student.builder()
                    .id(storage.getNewId())
                    .firstName("Andre" + i)
                    .lastName("David" + i)
                    .age(30 + i)
                    .build());
        }
    }
}
