package com.example.tasktracker.dto.task;

import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private TaskStatus status;

    private User author;

    private User assignee;

    private Set<User> observers;

}
