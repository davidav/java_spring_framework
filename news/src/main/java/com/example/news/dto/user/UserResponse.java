package com.example.news.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String firstName;

    private String secondName;

    private Integer countNewses;

    private Integer countComments;

}
