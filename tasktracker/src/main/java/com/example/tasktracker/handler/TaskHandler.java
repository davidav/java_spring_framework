package com.example.tasktracker.handler;

import com.example.tasktracker.dto.task.TaskModel;
import com.example.tasktracker.dto.user.UserModel;
import com.example.tasktracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskHandler {

    private final TaskService taskService;

    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        log.info("TaskHandler -> getAll");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(taskService.findAll(), TaskModel.class));
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        log.info("TaskHandler -> findById: {}", serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(
                        taskService.findById(serverRequest.pathVariable("id")), TaskModel.class));
    }
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TaskModel.class)
                .flatMap(taskModel -> {
                    log.info("TaskHandler -> create: {}", taskModel);
                    return taskService.save(taskModel);
                })
                .flatMap(task -> ServerResponse.created(URI.create("/api/v1/task/" + task.getId()))
                        .build());
    }

    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TaskModel.class)
                .map(taskModel -> {
                    log.info("TaskHandler -> update: {}", taskModel);
                    return taskService.update(serverRequest.pathVariable("id"), taskModel);
                })
                .flatMap(taskModelMono ->
                        ServerResponse.ok()
                                .body(BodyInserters.fromProducer(taskModelMono, TaskModel.class)));
    }

    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        log.info("TaskHandler -> deleteById: {}", serverRequest.pathVariable("id"));
        return ServerResponse.ok()
                .body(BodyInserters.fromProducer(
                        taskService.deleteById(serverRequest.pathVariable("id")), Void.class));
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public Mono<ServerResponse> addAssignee(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserModel.class)
                .map(assignee -> {
                    log.info("TaskHandler -> add assignee {} into task with id {}",
                            assignee,
                            serverRequest.pathVariable("id"));
                    return taskService.addAssignee(serverRequest.pathVariable("id"), assignee);
                })
                .flatMap(taskModelMono ->
                        ServerResponse.ok()
                                .body(BodyInserters.fromProducer(taskModelMono, TaskModel.class)));
    }

}


