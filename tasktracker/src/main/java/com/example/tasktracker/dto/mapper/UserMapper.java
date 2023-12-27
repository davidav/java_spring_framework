package com.example.tasktracker.dto.mapper;

import com.example.tasktracker.dto.user.UserResponse;
import com.example.tasktracker.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Mono;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {


    Mono<UserResponse> userToResponse(Mono<User> user);

    Mono<User> responseToUser(Mono<UserResponse> response);

}
