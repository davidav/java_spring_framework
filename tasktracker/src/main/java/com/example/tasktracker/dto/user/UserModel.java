package com.example.tasktracker.dto.user;

import entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private String id;

    private String username;

    private String email;

    private String password;

    private Set<RoleType> roles;

}
