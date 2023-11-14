package com.example.rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApiDescription(){
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local env");

        Server proServer = new Server();
        proServer.setUrl("http://some.prod.url");
        proServer.setDescription("Production env");

        Contact contact = new Contact();
        contact.setName("Ivan N");
        contact.setEmail("some@example");
        contact.setUrl("http://some.url");

        License mitLicense = new License().name("GNU").url("https://some.license.url");

        Info info = new Info()
                .title("Client orders API")
                .version("1.0")
                .contact(contact)
                .description("API for client orders")
                .termsOfService("https://some.terms.url")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(localServer, proServer));


    }

}
