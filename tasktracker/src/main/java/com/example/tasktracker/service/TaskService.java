package com.example.tasktracker.service;

import com.example.tasktracker.dto.mapper.TaskMapper;
import com.example.tasktracker.dto.task.TaskModel;
import com.example.tasktracker.dto.user.UserModel;
import entity.Task;
import com.example.tasktracker.repo.TaskRepository;
import com.example.tasktracker.util.AppHelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserService userService;

    public Flux<TaskModel> findAll() {
        Flux<Task> tasksFromDB = taskRepository.findAll();

        return tasksFromDB.flatMap(task -> {
            TaskModel taskModel = taskMapper.taskToModel(task);
            log.info("TaskService -> findAll - task: {}", task);

            return Mono.zip(userService.findById(task.getAuthorId()),
                            userService.findById(task.getAssigneeId()),
                            userService.findAllById(task.getObserverIds()).collectList())
                    .flatMap(tuple -> {
                        taskModel.setAuthor((UserModel) tuple.get(0));
                        taskModel.setAssignee((UserModel) tuple.get(1));
                        taskModel.setObservers(new HashSet<>((Collection) tuple.get(2)));
                        log.info("TaskService -> findAll - observes; {}", taskModel.getObservers().toString());
                        return Mono.just(taskModel);
                    });
        });
    }

    public Mono<TaskModel> findById(String id) {
        log.info("TaskService -> findById: {}", id);
        Mono<Task> taskMono = taskRepository.findById(id);

        return taskMono.flatMap(task -> {
            TaskModel taskModel = taskMapper.taskToModel(task);

            return Mono.zip(userService.findById(task.getAuthorId()),
                            userService.findById(task.getAssigneeId()),
                            userService.findAllById(task.getObserverIds()).collectList())
                    .flatMap(tuple -> {
                        taskModel.setAuthor((UserModel) tuple.get(0));
                        taskModel.setAssignee((UserModel) tuple.get(1));
                        taskModel.setObservers(new HashSet<>((Collection<UserModel>) tuple.get(2)));
                        return Mono.just(taskModel);
                    });
        });

    }

    public Mono<TaskModel> save(TaskModel taskModel) {
        Task task = taskMapper.modelToTask(taskModel);
        task.setId(UUID.randomUUID().toString());
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        task.setAuthorId(taskModel.getAuthor().getId());
        task.setAssigneeId(taskModel.getAssignee().getId());
        task.setObserverIds(taskModel.getObservers().stream()
                .map(UserModel::getId).collect(Collectors.toSet()));
        log.info("TaskService -> save - task before save to repo : {}", task);
        return taskRepository.save(task).map(taskMapper::taskToModel);
    }

    public Mono<TaskModel> update(String id, TaskModel updateTaskModel) {
        Task updateTask = taskMapper.modelToTask(updateTaskModel);

        return taskRepository.findById(id).map(existedTask -> {
                    existedTask.setId(id);
                    AppHelperUtils.copyNonNullProperties(updateTask, existedTask);
                    if (updateTaskModel.getAuthor() != null) {
                        existedTask.setAuthorId(updateTaskModel.getAuthor().getId());
                    }
                    if (updateTaskModel.getAssignee() != null) {
                        existedTask.setAssigneeId(updateTaskModel.getAssignee().getId());
                    }
                    if (!updateTaskModel.getObservers().isEmpty()) {
                        existedTask.setObserverIds(
                                updateTaskModel.getObservers().stream()
                                        .map(UserModel::getId)
                                        .collect(Collectors.toSet()));
                    }
                    log.info("TaskService -> update - task before repo : {}", existedTask);
                    return existedTask;
                }).flatMap(taskRepository::save)
                .map(taskMapper::taskToModel);
    }

    public Mono<Void> deleteById(String id) {
        log.info("UserService -> deleteById: {}", id);
        return taskRepository.deleteById(id);
    }

    public Mono<TaskModel> addAssignee(String id, UserModel assignee) {
        Mono<Task> existedTask = taskRepository.findById(id);

        return existedTask.flatMap(task -> {
            task.addAssignee(assignee.getId());
            return taskRepository.save(task).map(taskMapper::taskToModel);

        });
    }

}
