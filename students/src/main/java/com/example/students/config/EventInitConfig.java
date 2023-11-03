package com.example.students.config;

import com.example.students.db.Storage;
import com.example.students.service.StudentInitialization;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ConditionalOnProperty("app.init.enabled")
public class EventInitConfig {

    @Bean
    public StudentInitialization studentInitialization(Storage storage){
        return new StudentInitialization(storage);
    }

}
