package com.example.news.controller;

import com.example.news.StringTestUtils;
import com.example.news.dto.news.UpsertNewsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NewsControllerTest extends AbstractTest {

    @Test
    @WithUserDetails(userDetailsServiceBeanName = "userDetailsServiceImpl", value = "user",
            setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void updateNewsRequestFromAuthor_ReturnUpdatedNews() throws Exception {

        UpsertNewsRequest request = new UpsertNewsRequest();
        request.setCategoryId(1L);
        request.setTitle("Update title");
        request.setText("Update text Update text Update text in news is author - user");

        mockMvc.perform(put("/api/news/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(StringTestUtils.readStringFromResource(
                        "response/updateNewsRequestFromAuthor.json")));

    }


}