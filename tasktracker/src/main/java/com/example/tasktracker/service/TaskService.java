package com.example.tasktracker.service;

import com.example.tasktracker.dto.mapper.TaskMapper;
import com.example.tasktracker.dto.mapper.UserMapper;
import com.example.tasktracker.dto.task.TaskModel;
import com.example.tasktracker.dto.user.UserModel;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;


//    public Flux<TaskModel> findAll() {
//        Flux<Task> tasksFromDB = taskRepository.findAll();
//
//        return tasksFromDB.flatMap(task -> {
//            Mono<UserModel> userAuthor = userService.findById(task.getAuthorId());
//            Mono<UserModel> userAssignee = userService.findById(task.getAssigneeId());
//            Flux<UserModel> userObservers = userService.findAllById(task.getObserverIds());
//            return null;
//        });
//    }

}
