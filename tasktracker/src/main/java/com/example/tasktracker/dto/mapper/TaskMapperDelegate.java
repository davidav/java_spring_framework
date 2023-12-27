package com.example.tasktracker.dto.mapper;

import com.example.tasktracker.dto.task.TaskResponse;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class TaskMapperDelegate implements TaskMapper{

    @Override
    public Mono<TaskResponse> taskToResponse(Mono<Task> task, Mono<User> author, Mono<User> assignee, Flux<User> observers) {
        return null;
    }
}
