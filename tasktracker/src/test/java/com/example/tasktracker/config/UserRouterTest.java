package com.example.tasktracker.config;

import com.example.tasktracker.AbstractTest;
import com.example.tasktracker.dto.user.UserModel;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserRouterTest extends AbstractTest {

    @Test
    public void whenGetAllUsers_thenReturnListOfUsersFromDB(){
        var expectedData = List.of(
                        new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com"),
                        new UserModel(SECOND_USER_ID, "user2", "email2@qwe.com"));

        webTestClient.get().uri("/api/v1/user")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserModel.class)
                .hasSize(2)
                .contains(expectedData.toArray(UserModel[]::new));

    }

    @Test
    public void whenGetUserById_thenReturnUserFromDB(){
        var expectedData = new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com");

        webTestClient.get().uri("/api/v1/user/{id}", FIRST_USER_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserModel.class)
                .isEqualTo(expectedData);

    }

    @Test
    public void whenCreateUser_thenReturnNewUser(){
        StepVerifier.create(userRepository.count())
                .expectNext(2L)
                .expectComplete()
                .verify();

        UserModel requestModel = new UserModel();
        requestModel.setUsername("Test");
        requestModel.setEmail("test@asd.com");

        webTestClient.post().uri("/api/v1/user")
                .body(Mono.just(requestModel), UserModel.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserModel.class);

        StepVerifier.create(userRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();

    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUser(){

        UserModel requestModel = new UserModel();
        requestModel.setUsername("New name");

        webTestClient.put().uri("/api/v1/user/{id}", FIRST_USER_ID)
                .body(Mono.just(requestModel), UserModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserModel.class)
                .value(responseModel -> {
                    assertEquals("New name", responseModel.getUsername());
                });

    }

    @Test
    public void whenDeleteUser_thenRemoveUserFromDB(){

        StepVerifier.create(userRepository.count())
                .expectNext(2L)
                .expectComplete()
                .verify();

        webTestClient.delete().uri("/api/v1/user/{id}", FIRST_USER_ID)
                .exchange()
                .expectStatus().isOk();

        StepVerifier.create(userRepository.count())
                .expectNext(1L)
                .expectComplete()
                .verify();

    }

}
