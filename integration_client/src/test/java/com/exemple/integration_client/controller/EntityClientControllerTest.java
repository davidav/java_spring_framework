package com.exemple.integration_client.controller;

import com.exemple.integration_client.AbstractTest;
import com.exemple.integration_client.entity.DataBaseEntity;
import com.exemple.integration_client.model.UpsertEntityRequest;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EntityClientControllerTest extends AbstractTest{

    @Test
    public void whenGetAllEntities_thenReturnEntityList() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(dataBaseEntityService.findAll());

        assertFalse(redisTemplate.keys("*").isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

    @Test
    public void whenGetEntityByName_thenReturnEntity() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());

        String actualResponse = mockMvc.perform(get("/api/v1/entity/testName_1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(dataBaseEntityService.findByName("testName_1"));

        assertFalse(redisTemplate.keys("*").isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);

    }

//    @Test
//    public void whenGetEntityById_thenReturnEntity() throws Exception {
//        assertTrue(redisTemplate.keys("*").isEmpty());
//
//        String actualResponse = mockMvc.perform(get("/api/v1/entity"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//        String expectedResponse = objectMapper.writeValueAsString(dataBaseEntityService.findAll());
//
//        assertFalse(redisTemplate.keys("*").isEmpty());
//        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
//
//    }

    @Test
    public void whenCreateEntity_thenCreateEntityAndEvictCache() throws Exception {


        mockMvc.perform(get("/api/v1/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());

        UpsertEntityRequest request = new UpsertEntityRequest();
        request.setName("newEntity");
        String actualResponse = mockMvc.perform(post("/api/v1/entity")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(new DataBaseEntity(UUID.randomUUID(),"newEntity", ENTITY_DATE));

        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(4, dataBaseEntityRepository.count());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse, JsonAssert.whenIgnoringPaths("id"));

    }

    @Test
    public void whenUpdateEntity_thenUpdateEntityAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());



        assertFalse(redisTemplate.keys("*").isEmpty());

        UpsertEntityRequest request = new UpsertEntityRequest();
        request.setName("updateEntity");
        String actualResponse = mockMvc.perform(put("/api/v1/entity/{id}", UPDATE_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(new DataBaseEntity(UPDATE_ID,"updateEntity", ENTITY_DATE));

        assertTrue(redisTemplate.keys("*").isEmpty());
        JsonAssert.assertJsonEquals(expectedResponse, actualResponse);
    }

    @Test
    public void whenDeleteEntityById_thenDeleteEntityByIdAndEvictCache() throws Exception {
        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(3, dataBaseEntityRepository.count());

        mockMvc.perform(get("/api/v1/entity"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertFalse(redisTemplate.keys("*").isEmpty());

        mockMvc.perform(delete("/api/v1/entity" + UPDATE_ID))
                .andExpect(status().isNoContent());

        assertTrue(redisTemplate.keys("*").isEmpty());
        assertEquals(2, dataBaseEntityRepository.count());
    }

}
