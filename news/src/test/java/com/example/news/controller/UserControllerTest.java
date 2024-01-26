package com.example.news.controller;

import com.example.news.StringTestUtils;
import com.example.news.dto.PagesRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends AbstractTest {

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "admin",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
//    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenFindAll_thenReturnAllUsers() throws Exception {


        mockMvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        StringTestUtils.readStringFromResource("response/find_all_users_response.json")));
    }
}