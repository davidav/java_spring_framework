package com.example.news.dto.mapper;

import com.example.news.dto.user.UpsertUserRequest;
import com.example.news.dto.user.UserListResponse;
import com.example.news.dto.user.UserResponse;
import com.example.news.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserListResponse userListToUserListResponse(List<User> users);


    UserResponse userToResponse(User user);


    User requestToUser(UpsertUserRequest request);

    User requestToUser(Long id, UpsertUserRequest request);
}
