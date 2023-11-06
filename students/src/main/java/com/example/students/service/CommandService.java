package com.example.students.service;

import com.example.students.db.Storage;
import com.example.students.model.Student;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;


@ShellComponent
@AllArgsConstructor
public class CommandService {

    private final Storage storage;

    @ShellMethod(key = "all", value = "show all students from database")
    public String showAll() {
        List<Student> students = storage.getAll();
        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append(student.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @ShellMethod(key = "save", value = "save student into database, parameters: --f first name, --l last name, --a age")
    public void saveStudent(
            @ShellOption(value = "f") String firstName,
            @ShellOption(value = "l") String lastName,
            @ShellOption(value = "a") int age) {
        storage.save(Student.builder()
                .id(storage.getNewId())
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build());
    }

    @ShellMethod(key = "del", value = "delete student from database, parameters: --id number")
    public void deleteStudent(int id) {
        storage.delete(id);
    }

    @ShellMethod(key = "cl", value = "delete all students from database")
    public String clearAll() {
        storage.clear();
        return "All students deleted";
    }


}
