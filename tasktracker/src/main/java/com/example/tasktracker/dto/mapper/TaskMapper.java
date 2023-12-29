package com.example.tasktracker.dto.mapper;

import com.example.tasktracker.dto.task.TaskModel;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;


@DecoratedWith(TaskMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
//    TaskModel taskToModel(Task task);

    Task modelToTask(TaskModel model);

    TaskModel taskToModel(Task task);

//    TaskModel taskToModel(Task task, User author, User assignee, Set<User> observers);
//
//    Task modelToTask(TaskModel taskModel);

}
