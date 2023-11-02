package com.example.students.model;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {

    private int id;

    private String firstName;

    private String lastName;

    private int age;

    @Override
    public String toString() {
        return firstName + " " + lastName + ", age=" + age + ", id=" + id;
    }
}
