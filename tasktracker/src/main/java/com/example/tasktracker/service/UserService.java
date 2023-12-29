package com.example.tasktracker.service;


import com.example.tasktracker.dto.mapper.UserMapper;
import com.example.tasktracker.dto.user.UserModel;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Flux<UserModel> findAll() {
        log.info("UserService -> findAll");
        return userRepository.findAll().map(userMapper::userToModel);
    }


    public Mono<UserModel> findById(String id) {
        log.info("UserService -> findById: {}", id);
        return userRepository.findById(id).map(userMapper::userToModel);
    }


    public Mono<UserModel> save(UserModel userModel) {
        User user = userMapper.modelToUser(userModel);
        user.setId(UUID.randomUUID().toString());
        log.info("UserService -> save: {}", user);
        return userRepository.save(user).map(userMapper::userToModel);
    }


    public Mono<UserModel> update(String id, UserModel updateUserModel) {
        User updateUser = userMapper.modelToUser(updateUserModel);
        return userRepository.findById(id).map(existedUser -> {
                    existedUser.setId(id);
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
        log.info("UserService -> findAllById");
        return userRepository.findAllById(observerIds).map(userMapper::userToModel);

    }
}
