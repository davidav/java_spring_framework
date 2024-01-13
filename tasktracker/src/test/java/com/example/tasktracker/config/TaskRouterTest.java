package com.example.tasktracker.config;

import com.example.tasktracker.AbstractTest;
import com.example.tasktracker.dto.task.TaskModel;

import com.example.tasktracker.dto.user.UserModel;
import com.example.tasktracker.entity.TaskStatus;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskRouterTest extends AbstractTest {

    @Test
    public void whenGetAllTasks_thenReturnListOfTasksFromDB() {
        var expectedData = List.of(
                new TaskModel(FIRST_TASK_ID,
                        "TaskName1",
                        "TaskDescription1",
                        instant,
                        instant,
                        TaskStatus.TODO,
                        new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com"),
                        new UserModel(SECOND_USER_ID, "user2", "email2@qwe.com"),
                        Set.of(new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com"),
                                new UserModel(SECOND_USER_ID, "user2", "email2@qwe.com"))));

        webTestClient.get().uri("/api/v1/task")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskModel.class)
                .hasSize(1)
                .contains(expectedData.toArray(TaskModel[]::new));

    }

    @Test
    public void whenGetTaskById_thenReturnTaskFromDB() {
        var expectedData = new TaskModel(FIRST_TASK_ID,
                "TaskName1",
                "TaskDescription1",
                instant,
                instant,
                TaskStatus.TODO,
                new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com"),
                new UserModel(SECOND_USER_ID, "user2", "email2@qwe.com"),
                Set.of(new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com"),
                        new UserModel(SECOND_USER_ID, "user2", "email2@qwe.com")));

        webTestClient.get().uri("/api/v1/task/{id}", FIRST_TASK_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskModel.class)
                .isEqualTo(expectedData);

    }

    @Test
    public void whenCreateTask_thenReturnNewTask() {
        StepVerifier.create(taskRepository.count())
                .expectNext(1L)
                .expectComplete()
                .verify();

        TaskModel requestModel = new TaskModel();
        requestModel.setName("TaskName");
        requestModel.setDescription("TaskDescription");
        requestModel.setCreatedAt(instant);
        requestModel.setUpdatedAt(instant);
        requestModel.setStatus(TaskStatus.TODO);
        requestModel.setAuthor(new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com"));
        requestModel.setAssignee(new UserModel(SECOND_USER_ID, "user2", "email2@qwe.com"));
        requestModel.setObservers(Set.of(new UserModel(FIRST_USER_ID, "user1", "email1@qwe.com"),
                new UserModel(SECOND_USER_ID, "user2", "email2@qwe.com")));

        webTestClient.post().uri("/api/v1/task")
                .body(Mono.just(requestModel), TaskModel.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TaskModel.class);

        StepVerifier.create(taskRepository.count())
                .expectNext(2L)
                .expectComplete()
                .verify();

    }

    @Test
    public void whenUpdateTask_thenReturnUpdatedTask() {

        TaskModel requestModel = new TaskModel();
        requestModel.setName("Task updated");

        webTestClient.put().uri("/api/v1/task/{id}", FIRST_TASK_ID)
                .body(Mono.just(requestModel), TaskModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskModel.class)
                .value(responseModel -> {
                    assertEquals("Task updated", responseModel.getName());
                });
    }

    @Test
    public void whenDeleteTask_thenRemoveTaskFromDB() {

        StepVerifier.create(taskRepository.count())
                .expectNext(1L)
                .expectComplete()
                .verify();

        webTestClient.delete().uri("/api/v1/task/{id}", FIRST_TASK_ID)
                .exchange()
                .expectStatus().isOk();

        StepVerifier.create(taskRepository.count())
                .expectNext(0L)
                .expectComplete()
                .verify();

    }

}
