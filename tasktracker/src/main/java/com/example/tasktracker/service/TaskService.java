package com.example.tasktracker.service;

import com.example.tasktracker.dto.mapper.TaskMapper;
import com.example.tasktracker.dto.mapper.UserMapper;
import com.example.tasktracker.dto.task.TaskModel;
import com.example.tasktracker.dto.user.UserModel;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    public Flux<TaskModel> findAll() {
        Flux<Task> tasksFromDB = taskRepository.findAll();

        return tasksFromDB.flatMap(task -> {
            Mono<UserModel> userAuthor = userService.findById(task.getAuthorId());
            Mono<UserModel> userAssignee = userService.findById(task.getAssigneeId());
            Flux<UserModel> userObservers = userService.findAllById(task.getObserverIds());
            return null;
        });
    }

    public Mono<TaskModel> findById(String id) {
        log.info("TaskService -> findById: {}", id);
        return taskRepository.findById(id).map(taskMapper::taskToModel);
    }

    public Mono<TaskModel> save(TaskModel taskModel) {
        Task task = taskMapper.modelToTask(taskModel);
        task.setId(UUID.randomUUID().toString());
        log.info("UserService -> save: {}", task);
        return taskRepository.save(task).map(taskMapper::taskToModel);
    }
    public Mono<TaskModel> update(String id, TaskModel updateTaskModel) {
        Task updateTask = taskMapper.modelToTask(updateTaskModel);
        return taskRepository.findById(id).map(existedTask -> {
                    existedTask.setId(id);
                    if (StringUtils.hasText(updateTask.getUsername())) {
                        existedTask.setUsername(updateUser.getUsername());
                    }
                    if (StringUtils.hasText(updateTask.getEmail())) {
                        existedTask.setEmail(updateUser.getEmail());
                    }
                    log.info("UserService -> before update: {}", updateTask);
                    return existedTask;
                }).flatMap(taskRepository::save)
                .map(taskMapper::taskToModel);
    }


    public Mono<Void> deleteById(String id) {
        log.info("UserService -> deleteById: {}", id);
        return taskRepository.deleteById(id);
    }

}
