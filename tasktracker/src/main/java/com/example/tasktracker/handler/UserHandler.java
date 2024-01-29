package com.example.tasktracker.handler;

import com.example.tasktracker.dto.user.UserModel;
import com.example.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class UserHandler {


    private final UserService userService;

    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(userService.findAll(), UserModel.class));
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(
                        userService.findById(serverRequest.pathVariable("id")), UserModel.class));
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserModel.class)
                .flatMap(userService::save)
                .flatMap(user -> ServerResponse.created(URI.create("/api/v1/user/" + user.getId()))
                        .build());
    }


    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserModel.class)
                .map(userModel -> {

                    return userService.update(serverRequest.pathVariable("id"), userModel);
                })
                .flatMap(userModelMono ->
                        ServerResponse.ok()
                                .body(BodyInserters.fromProducer(userModelMono, UserModel.class)));
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_USER','ROLE_MANAGER')")
    public Mono<ServerResponse> deleteById(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(BodyInserters.fromProducer(
                        userService.deleteById(serverRequest.pathVariable("id")), Void.class));
    }

    public Mono<ServerResponse> errorRequest(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.error(new RuntimeException("Exception in errorRequest")), String.class)
                .onErrorResume(ex -> {
                    return ServerResponse.badRequest().body(Mono.error(ex), String.class);
                });
    }
}