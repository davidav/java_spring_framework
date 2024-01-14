package com.example.tasktracker.config;

import com.example.tasktracker.handler.TaskHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TaskRouter {

    @Bean
    public RouterFunction<ServerResponse> taskRouters(TaskHandler taskHandler) {
        return RouterFunctions.route()
                .GET("api/v1/task", taskHandler::getAll)
                .GET("api/v1/task/{id}", taskHandler::findById)
                .POST("api/v1/task", taskHandler::create)
                .PUT("api/v1/task/{id}", taskHandler::update)
                .DELETE("api/v1/task/{id}", taskHandler::deleteById)
                .POST("api/v1/task/assignee/{taskId}", taskHandler::addAssignee)
                .build();

    }
}
