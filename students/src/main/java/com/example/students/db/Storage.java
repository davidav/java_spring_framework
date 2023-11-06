package com.example.students.db;

import com.example.students.model.Student;

import java.util.List;

public interface Storage {

    Student get(int id);

    List<Student> getAll();

    void save(Student student);

    void delete(int id);

    void clear();

    int getNewId();
}
