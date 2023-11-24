package com.example.news.dto.mapper;


import com.example.news.dto.user.UserResponse;
import com.example.news.model.User;



public abstract class UserMapperDelegate implements UserMapper{

    @Override
    public UserResponse userToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setSecondName(user.getSecondName());
        userResponse.setCountNewses(user.getNewses().size());
        userResponse.setCountComments(user.getComments().size());

        return userResponse;
    }



}


