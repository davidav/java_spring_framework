package com.example.springbasicauthexample.controller;

import com.example.springbasicauthexample.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractTest {

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenUserRequestMethodUserRole_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Method called by user: user. Role is: ROLE_USER"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void whenUserRequestMethodAdminRole_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Method called by user: admin. Role is: ROLE_ADMIN"));
    }

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "testUser",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    public void whenUserRequestMethodWithNameTestUser_thenReturnOk() throws Exception {
        mockMvc.perform(get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("Method called by user: testUser. Role is: ROLE_USER"));
    }
}