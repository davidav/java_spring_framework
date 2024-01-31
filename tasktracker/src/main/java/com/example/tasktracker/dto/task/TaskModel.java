package com.example.tasktracker.dto.task;

import com.example.tasktracker.dto.user.UserModel;
import entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {

    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;
    private UserModel author;
    private UserModel assignee;
    private Set<UserModel> observers;

}
