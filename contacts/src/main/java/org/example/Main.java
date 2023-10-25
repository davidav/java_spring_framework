package org.example;

import org.example.config.AppConfig;


import org.example.service.AppService;
import org.example.service.ContactLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        if (context.getEnvironment().getActiveProfiles()[0].equals("init")){
            System.out.println("Initialisation...");
            context.getBean(ContactLoader.class).load();
        }
        context.getBean(AppService.class).start();

    }
}