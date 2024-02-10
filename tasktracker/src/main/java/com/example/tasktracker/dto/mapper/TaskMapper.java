package com.example.tasktracker.dto.mapper;

import com.example.tasktracker.dto.task.TaskModel;

import com.example.tasktracker.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;



@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
    Task modelToTask(TaskModel model);
    TaskModel taskToModel(Task task);

}
