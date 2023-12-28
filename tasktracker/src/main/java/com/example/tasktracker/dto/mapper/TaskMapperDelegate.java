package com.example.tasktracker.dto.mapper;

import com.example.tasktracker.dto.task.TaskModel;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class TaskMapperDelegate implements TaskMapper{

//    @Override
//    public TaskModel taskToModel(Task task, User author, User assignee, Set<User> observers) {
//        TaskModel taskModel = new TaskModel();
//        BeanUtils.copyProperties(task, taskModel, "authorId", "assigneeId", "observerIds");
//        taskModel.setAuthor(author);
//        taskModel.setAssignee(assignee);
//        taskModel.setObservers(observers);
//
//        return taskModel;
//    }
//
//    @Override
//    public Task modelToTask(TaskModel taskModel) {
//        Task task = new Task();
//        BeanUtils.copyProperties(taskModel, task, "authorId", "assigneeId", "observerId");
//        task.setAuthorId(taskModel.getAuthor().getId());
//        task.setAssigneeId(taskModel.getAssignee().getId());
//        task.setObserverIds(taskModel.getObservers().stream()
//                .map(User::getId)
//                .collect(Collectors.toSet()));
//        return task;
//    }
}
