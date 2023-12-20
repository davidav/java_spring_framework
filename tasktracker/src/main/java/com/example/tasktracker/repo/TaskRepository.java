package com.example.tasktracker.repo;

import com.example.tasktracker.entity.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {



}
