package com.example.students.db;

import com.example.students.model.Student;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StorageImpl implements Storage {

    private final Map<Integer, Student> students = new HashMap<>();
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

    public Student save(Student student) {
        Student studentDB = students.put(student.getId(), student);
        if (studentDB == null) return student;
        return studentDB;
    }

    public Student delete(int id) {
        return students.remove(id);
    }

    public void clear() {
        students.clear();
    }

    public int getNewId() {
        return id++;
    }

}
