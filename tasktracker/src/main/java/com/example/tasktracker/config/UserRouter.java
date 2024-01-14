package com.example.tasktracker.config;

import com.example.tasktracker.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRouters(UserHandler userHandler){
        return RouterFunctions.route()
                .GET("api/v1/user", userHandler::getAll)
                .GET("api/v1/user/{id}", userHandler::findById)
                .POST("api/v1/user", userHandler::create)
                .PUT("api/v1/user/{id}",userHandler::update)
                .DELETE("api/v1/user/{id}", userHandler::deleteById)
                .build();
    }

}
