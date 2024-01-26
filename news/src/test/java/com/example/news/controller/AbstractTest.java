package com.example.news.controller;

import com.example.news.model.*;
import com.example.news.repository.CategoryRepository;
import com.example.news.repository.CommentRepository;
import com.example.news.repository.NewsRepository;
import com.example.news.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.testcontainers.utility.DockerImageName;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Testcontainers
public abstract class AbstractTest {

    protected static PostgreSQLContainer postgreSQLContainer;

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:12.3");
        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(postgres)
                .withReuse(true);
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();

        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", () -> jdbcUrl);
    }

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected NewsRepository newsRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        User admin = createUser("admin", "admin", RoleType.ROLE_ADMIN,
                "admin", "admin", null, null);
        Category category = createCategory("Name category 1", null);
        News news = createNews("Title news 1", "Text news 1 Text news 1 Text news 1", admin, category, null);
        Comment comment = createComment("This is comment 1 for news 1 from admin", admin, news);
        news.addComment(comment);
        admin.addComment(comment);
        admin.addNews(news);
        category.addNews(news);

    }

    @AfterEach
    public void afterEach() {
        commentRepository.deleteAll();
        newsRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    protected User createUser(String login, String password,
                              RoleType role, String firstName, String secondName,
                              News news, Comment comment) {
        User user = User.builder()
//                .id(id)
                .login(login)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList(Role.from(role)))
                .firstName(firstName)
                .secondName(secondName)
                .newses(new ArrayList<>())
                .comments(new ArrayList<>())
                .createAt(Instant.now())
                .build();

        if (news != null) {
            news.setUser(user);
            user.addNews(news);
        }

        if (comment != null) {
            comment.setUser(user);
            user.addComment(comment);
        }
        return userRepository.save(user);
    }

    protected Category createCategory(String name, News news) {
        Category category = Category.builder()
//                .id(id)
                .name(name)
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .newses(new ArrayList<>())
                .build();

        if (news != null) {
            news.setCategory(category);
            category.addNews(news);
        }

        return categoryRepository.save(category);
    }

    protected News createNews(String title, String text, User user, Category category, Comment comment) {

        News news = News.builder()
//                .id(id)
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .title(title)
                .text(text)
                .comments(new ArrayList<>())
                .build();

        if (user != null) {
            user.addNews(news);
            news.setUser(user);
        }

        if (category != null) {
            category.addNews(news);
            news.setCategory(category);
        }

        if (comment != null) {
            comment.setNews(news);
            news.addComment(comment);
        }

        return newsRepository.save(news);
    }

    protected Comment createComment(String comment, User user, News news) {
        Comment newComment = Comment.builder()
//                .id(id)
                .createAt(Instant.now())
                .updateAt(Instant.now())
                .comment(comment)
                .build();

        if (user != null) {
            user.addComment(newComment);
            newComment.setUser(user);
        }

        if (news != null) {
            news.addComment(newComment);
            newComment.setNews(news);
        }
        return commentRepository.save(newComment);
    }

}
