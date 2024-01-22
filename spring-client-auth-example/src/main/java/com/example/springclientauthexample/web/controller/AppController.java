package com.example.springclientauthexample.web.controller;

import com.example.springclientauthexample.client.BasicAuthClient;
import com.example.springclientauthexample.web.model.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class AppController {

    private final BasicAuthClient basicAuthClient;

    @PostMapping
    public Mono<String> authRequest(@RequestBody AuthRequest authRequest){
        return basicAuthClient.getData(authRequest);

    }
}
