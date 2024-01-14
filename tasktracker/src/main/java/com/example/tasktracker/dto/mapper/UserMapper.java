package com.example.tasktracker.dto.mapper;

import com.example.tasktracker.dto.user.UserModel;
import com.example.tasktracker.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserModel userToModel(User user);
    User modelToUser(UserModel model);

}
