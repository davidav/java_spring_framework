package com.example.tasktracker.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserFluxResponse {

    private List<UserResponse> users;

}
