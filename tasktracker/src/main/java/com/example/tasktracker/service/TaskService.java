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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    public Flux<TaskModel> findAll() {
        Flux<Task> tasksFromDB = taskRepository.findAll();

        return tasksFromDB.flatMap(task -> {
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

    public Mono<TaskModel> findById(String id) {
        log.info("TaskService -> findById: {}", id);
        Mono<Task> taskMono = taskRepository.findById(id);

        return taskMono.flatMap(task -> {
            TaskModel taskModel = taskMapper.taskToModel(task);

            return Mono.zip(userService.findById(task.getAuthorId()),
                            userService.findById(task.getAssigneeId()),
                            userService.findAllById(task.getObserverIds()).collectList())
                    .flatMap(tuple -> {
                        taskModel.setAuthor(userMapper.userToModel((User) tuple.get(0)));
                        taskModel.setAssignee((UserModel) tuple.get(1));
                        taskModel.setObservers(new HashSet<>((Collection<UserModel>) tuple.get(2)));
                        return Mono.just(taskModel);
                    });
        });

    }

    public Mono<TaskModel> save(TaskModel taskModel) {
        Task task = taskMapper.modelToTask(taskModel);
        log.info("TaskService -> save - task after mapper : {}", task);
        task.setId(UUID.randomUUID().toString());
        task.setAuthorId(taskModel.getAuthor().getId());
        task.setAssigneeId(taskModel.getAssignee().getId());
        task.setObserverIds(taskModel.getObservers().stream()
                .map(UserModel::getId).collect(Collectors.toSet()));
        return  taskRepository.save(task).map(taskMapper::taskToModel);
    }



    public Mono<TaskModel> update(String id, TaskModel updateTaskModel) {
        Task updateTask = taskMapper.modelToTask(updateTaskModel);
        return taskRepository.findById(id).map(existedTask -> {
                    existedTask.setId(id);
//                    if (StringUtils.hasText(updateTask.getUsername())) {
//                        existedTask.setUsername(updateUser.getUsername());
//                    }
//                    if (StringUtils.hasText(updateTask.getEmail())) {
//                        existedTask.setEmail(updateUser.getEmail());
//                    }
                    log.info("UserService -> before update: {}", updateTask);
                    return existedTask;
                }).flatMap(taskRepository::save)
                .map(taskMapper::taskToModel);
    }


    public Mono<Void> deleteById(String id) {
        log.info("UserService -> deleteById: {}", id);
        return taskRepository.deleteById(id);
    }

    public Mono<TaskModel> addAssignee(String id, UserModel assignee) {
        Mono<Task> existedTask = taskRepository.findById(id);//todo error handler

        return existedTask.flatMap(task -> {
            task.addAssignee(assignee.getId());
            return taskRepository.save(task).map(taskMapper::taskToModel);

        });
    }


}
