package com.example.tasktracker.service;


import com.example.tasktracker.dto.mapper.UserMapper;
import com.example.tasktracker.dto.user.UserModel;
import entity.User;
import com.example.tasktracker.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public Flux<UserModel> findAll() {
        log.info("UserService -> findAll");
        return userRepository.findAll().map(userMapper::userToModel);
    }


    public Mono<UserModel> findById(String id) {
        log.info("UserService -> findById: {}", id);
        return userRepository.findById(id)
                .defaultIfEmpty(new User("", "NO_USER_IN_DB", "", "",null))
                .map(userMapper::userToModel);
    }


    public Mono<UserModel> save(UserModel userModel) {
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        User user = userMapper.modelToUser(userModel);
        user.setId(UUID.randomUUID().toString());
        log.info("UserService -> save: {}", user);
        return userRepository.save(user).map(userMapper::userToModel);
    }


    public Mono<UserModel> update(String id, UserModel updateUserModel) {
        User updateUser = userMapper.modelToUser(updateUserModel);
        return userRepository.findById(id).map(existedUser -> {
                    if (StringUtils.hasText(updateUser.getUsername())) {
                        existedUser.setUsername(updateUser.getUsername());
                    }
                    if (StringUtils.hasText(updateUser.getEmail())) {
                        existedUser.setEmail(updateUser.getEmail());
                    }
                    log.info("UserService -> before update: {}", updateUser);
                    return existedUser;
                }).flatMap(userRepository::save)
                .map(userMapper::userToModel);
    }


    public Mono<Void> deleteById(String id) {
        log.info("UserService -> deleteById: {}", id);
        return userRepository.deleteById(id);
    }

    public Flux<UserModel> findAllById(Set<String> observerIds) {
        log.info("UserService -> findAllById - ids: ");
        return userRepository.findAllById(observerIds)
                .defaultIfEmpty(new User("", "NO_USER_IN_DB", "", "",null))
                .map(userMapper::userToModel);

    }

    public Mono<User> findByUsername(String username){
        return userRepository.findByUsername(username).defaultIfEmpty(new User("", "NO_USER_IN_DB", "", "",null));
    }

}
