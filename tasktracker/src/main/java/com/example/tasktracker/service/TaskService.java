package com.example.tasktracker.service;

import com.example.tasktracker.dto.mapper.TaskMapper;
import com.example.tasktracker.dto.mapper.UserMapper;
import com.example.tasktracker.dto.task.TaskResponse;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.repo.TaskRepository;
import com.example.tasktracker.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;


    public Flux<TaskResponse> findAll() {

        return null;
//        Flux<Task> tasksFromDB = taskRepository.findAll();
//        tasksFromDB.flatMap(task -> {
//            Mono<User> userAuthor = userRepository.findById(task.getAuthorId());
//            Mono<User> userAssignee = userRepository.findById(task.getAssigneeId());
//            Flux<User> userObservers = userRepository.findAllById(task.getObserverIds());
//            return taskMapper.taskToResponse(task, userAuthor, userAssignee, userObservers);
//        });

    }

}
