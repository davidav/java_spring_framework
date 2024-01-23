package com.example.springreactiveauthexample.web.controller;

import com.example.springreactiveauthexample.entity.Role;
import com.example.springreactiveauthexample.entity.RoleType;
import com.example.springreactiveauthexample.entity.User;
import com.example.springreactiveauthexample.service.UserService;
import com.example.springreactiveauthexample.web.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;

    @GetMapping
    public Mono<ResponseEntity<String>> publicMethod(){
        return Mono.just(ResponseEntity.ok("Public method calling"));
    }

    @PostMapping("/account")
    public Mono<ResponseEntity<UserDto>> createUserAccount(@RequestBody UserDto userDto, @RequestParam RoleType type){

        return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                .body(createAccount(userDto, type)));
    }

    private UserDto createAccount(UserDto userDto, RoleType type) {
        var user = new User();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        var createUser = userService.createNewAccount(user, Role.from(type));

        return UserDto.builder()
                .username(createUser.getUsername())
                .password(createUser.getPassword())
                .build();
    }

}
