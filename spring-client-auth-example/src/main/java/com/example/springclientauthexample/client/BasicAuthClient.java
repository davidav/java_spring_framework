package com.example.springclientauthexample.client;

import com.example.springclientauthexample.web.model.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BasicAuthClient {

    private final WebClient defaultWebClient;

    public Mono<String> getData(AuthRequest authRequest){
        return defaultWebClient.get()
                .uri("/api/v1/user")
                .headers(h -> h.setBasicAuth(authRequest.getUsername(), authRequest.getPassword()))
                .retrieve()
                .bodyToMono(String.class);
    }



}
