package com.example.springbasicauthexample.controller;

import com.example.springbasicauthexample.entity.Role;
import com.example.springbasicauthexample.entity.RoleType;
import com.example.springbasicauthexample.entity.User;
import com.example.springbasicauthexample.model.UserDto;
import com.example.springbasicauthexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> getPublic() {
        return ResponseEntity.ok("Called public method");
    }

    @PostMapping("/account")
    public ResponseEntity<UserDto> createUserAccount(@RequestBody UserDto userDto, @RequestParam RoleType roleType) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createAccount(userDto, roleType));
    }

    private UserDto createAccount(UserDto userDto, RoleType roleType) {
        var user = new User();
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());

        var createUser = userService.createNewAccount(user, Role.from(roleType));
        return UserDto.builder()
                .username(createUser.getUsername())
                .password(createUser.getPassword())
                .build();
    }
}
