package com.example.springbasicauthexample;

import com.example.springbasicauthexample.entity.Role;
import com.example.springbasicauthexample.entity.RoleType;
import com.example.springbasicauthexample.entity.User;
import com.example.springbasicauthexample.repo.UserRepository;
import com.example.springbasicauthexample.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@Testcontainers
public class AbstractTest  {

    protected static PostgreSQLContainer postgreSQLContainer;

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:12.3");

        postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(postgres)
                .withReuse(true);
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry){
        String jdbcUrl = postgreSQLContainer.getJdbcUrl();

        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", () -> jdbcUrl);
    }
    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        userService.createNewAccount(
                User.builder()
                        .username("testUser")
                        .password("12345")
                        .build()
        , Role.from(RoleType.ROLE_USER));
        userService.createNewAccount(
                User.builder()
                        .username("admin")
                        .password("admin")
                        .build()
                , Role.from(RoleType.ROLE_ADMIN));
    }

    @AfterEach
    public void after(){
        userRepository.deleteAll();
    }



}
