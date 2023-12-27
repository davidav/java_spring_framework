package com.example.tasktracker.dto.mapper;

import com.example.tasktracker.dto.task.TaskResponse;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@DecoratedWith(TaskMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {


    Mono<TaskResponse> taskToResponse(Mono<Task>  task, Mono<User> author, Mono<User> assignee, Flux<User> observers);


}
