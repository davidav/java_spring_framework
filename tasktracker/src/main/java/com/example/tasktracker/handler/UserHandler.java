package com.example.tasktracker.handler;

import com.example.tasktracker.dto.mapper.UserMapper;
import com.example.tasktracker.dto.user.UserFluxResponse;
import com.example.tasktracker.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserHandler {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(userMapper.(userRepository.findAll())));
        return null;
    }

}
