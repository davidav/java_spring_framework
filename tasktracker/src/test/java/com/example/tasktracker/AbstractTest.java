package com.example.tasktracker;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.repo.TaskRepository;
import com.example.tasktracker.repo.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public abstract class AbstractTest {

    protected static String FIRST_USER_ID = UUID.randomUUID().toString();
    protected static String SECOND_USER_ID = UUID.randomUUID().toString();
    protected static String FIRST_TASK_ID = UUID.randomUUID().toString();
    protected static Instant instant = Instant.now();
    protected static User user1 = new User(FIRST_USER_ID, "user1", "email1@qwe.com");
    protected static User user2 = new User(SECOND_USER_ID, "user2", "email2@qwe.com");

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.8")
            .withReuse(true);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TaskRepository taskRepository;

    @BeforeEach
    public void setup() {
        userRepository.saveAll(List.of(user1, user2))
                .collectList().block();
        taskRepository.saveAll(List.of(
                new Task(FIRST_TASK_ID,
                        "TaskName1",
                        "TaskDescription1",
                        instant,
                        instant,
                        TaskStatus.TODO,
                        FIRST_USER_ID,
                        SECOND_USER_ID,
                        Set.of(FIRST_USER_ID, SECOND_USER_ID),
                        user1,
                        user2,
                        Set.of(user1, user2))))
                .collectList().block();
    }

    @AfterEach
    public void afterEach(){
        taskRepository.deleteAll().block();
        userRepository.deleteAll().block();
    }

}
