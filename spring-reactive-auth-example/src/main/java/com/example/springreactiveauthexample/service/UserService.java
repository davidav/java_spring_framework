package com.example.springreactiveauthexample.service;

import com.example.springreactiveauthexample.entity.Role;
import com.example.springreactiveauthexample.entity.User;
import com.example.springreactiveauthexample.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }

    public User createNewAccount(User user, Role role){
        user.setRoles(Collections.singletonList(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        role.setUser(user);
        return userRepository.save(user);
    }

}
