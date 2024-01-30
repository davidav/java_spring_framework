package com.example.booking.dto.mapper;


import com.example.booking.dto.user.UpsertUserRequest;
import com.example.booking.dto.user.UserResponse;
import com.example.booking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse userToResponse(User user);

    User requestToUser(UpsertUserRequest request);

    User requestToUser(Long id, UpsertUserRequest request);

}
