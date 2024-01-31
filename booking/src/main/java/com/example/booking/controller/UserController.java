package com.example.booking.controller;

import com.example.booking.dto.mapper.UserMapper;
import com.example.booking.dto.user.UpsertUserRequest;
import com.example.booking.dto.user.UserResponse;
import com.example.booking.entity.RoleType;
import com.example.booking.entity.User;
import com.example.booking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                userMapper.userToResponse(userService.findById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestParam RoleType roleType, @RequestBody @Valid UpsertUserRequest request) {
        log.info("UserController -> create roleType={} request={}", roleType, request);
        User newUser = userService.save(userMapper.requestToUser(request), roleType);
        log.info("UserController -> create after service user={}", newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody  @Valid UpsertUserRequest request) {
        User updateUser = userService.update(userMapper.requestToUser(id, request));

        return ResponseEntity.ok(userMapper.userToResponse(updateUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
