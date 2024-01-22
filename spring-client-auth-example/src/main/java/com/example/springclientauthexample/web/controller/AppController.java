package com.example.springclientauthexample.web.controller;

import com.example.springclientauthexample.client.BasicAuthClient;
import com.example.springclientauthexample.client.JwtAuthClient;
import com.example.springclientauthexample.web.model.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class AppController {

//    private final BasicAuthClient basicAuthClient;
    private final JwtAuthClient jwtAuthClient;

//    @PostMapping
//    public Mono<String> authRequest(@RequestBody AuthRequest authRequest){
//        return basicAuthClient.getData(authRequest);
//
//    }

    @GetMapping
    public Mono<String> authRequest(){
        return jwtAuthClient.getData();
    }

}
