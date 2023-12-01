package com.exemple.integration_client.controller;

import com.exemple.integration_client.clients.OkHttpClientSender;
import com.exemple.integration_client.clients.OpenFeignClient;
import com.exemple.integration_client.clients.RestTemplateClient;
import com.exemple.integration_client.clients.WebClientSender;
import com.exemple.integration_client.entity.EntityModel;
import com.exemple.integration_client.entity.UpsertEntityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/client/entity")
@RequiredArgsConstructor
public class EntityClientController {

//    private final OkHttpClientSender client;
//    private final RestTemplateClient client;
//    private final WebClientSender client;
    private final OpenFeignClient client;

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList(){
        return ResponseEntity.ok(client.getList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name){
        return ResponseEntity.ok(client.getEntityByName(name));
    }

    @PostMapping()
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest upsertEntityRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(client.createEntity(upsertEntityRequest));
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest upsertEntityRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(client.updateEntity(id, upsertEntityRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel> deleteEntity(@PathVariable UUID id){
        client.deleteEntityById(id);
        return ResponseEntity.noContent().build();
    }



}
