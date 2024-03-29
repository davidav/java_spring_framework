package com.example.soringjwtauthexemple.web.controller;

import com.example.soringjwtauthexemple.exception.AlreadyExistsException;
import com.example.soringjwtauthexemple.repo.UserRepository;
import com.example.soringjwtauthexemple.security.SecurityService;
import com.example.soringjwtauthexemple.web.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody CreateUserRequest createUserRequest) {
        if (userRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new AlreadyExistsException("Username already exist");
        }

        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new AlreadyExistsException("Email already exist");
        }

        securityService.register(createUserRequest);

        return ResponseEntity.ok(new SimpleResponse("User created"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(securityService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        securityService.logout();

        return ResponseEntity.ok(new SimpleResponse("User logout. Username is: " + userDetails.getUsername()));
    }


}
