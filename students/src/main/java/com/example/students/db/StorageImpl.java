package com.example.students.db;

import com.example.students.event.EventDeleteStudent;
import com.example.students.event.EventSaveStudent;
import com.example.students.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class StorageImpl implements Storage {

    private final Map<Integer, Student> students = new HashMap<>();

    private final ApplicationEventPublisher publisher;
    private int id = 1;

    public Student get(int id) {
        Student student = students.get(id);
        if (student == null) {
            System.out.println("not found");
        }
        return student;
    }

    public List<Student> getAll() {
        Collection<Student> values = students.values();
        if (values.size() == 0) {
            System.out.println("student's book is empty");
        }
        return new ArrayList<>(values);
    }

    public void save(Student student) {
        Student studentDB = students.put(student.getId(), student);
        if (studentDB == null){
            publisher.publishEvent(new EventSaveStudent(this, student));
        }
    }

    public void delete(int id) {
        students.remove(id);
        publisher.publishEvent(new EventDeleteStudent(this, id));
    }

    public void clear() {
        students.clear();
    }

    public int getNewId() {
        return id++;
    }

}
