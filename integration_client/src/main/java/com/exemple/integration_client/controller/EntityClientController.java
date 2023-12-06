package com.exemple.integration_client.controller;

import com.exemple.integration_client.clients.OpenFeignClient;
import com.exemple.integration_client.entity.DataBaseEntity;
import com.exemple.integration_client.model.EntityModel;
import com.exemple.integration_client.model.UpsertEntityRequest;
import com.exemple.integration_client.service.DataBaseEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/client/entity")
@RequiredArgsConstructor
public class EntityClientController {

    //    private final OkHttpClientSender client;
    //    private final RestTemplateClient client;
    //    private final WebClientSender client;
    private final OpenFeignClient client;

    private final DataBaseEntityService service;

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList() {
    //        return ResponseEntity.ok(client.getList());
        return ResponseEntity.ok(service.findAll().stream().map(EntityModel::from).collect(Collectors.toList()));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<EntityModel> entityById(@PathVariable UUID id){
        return ResponseEntity.ok(EntityModel.from(service.findById(id)));
    }


    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name) {
    //        return ResponseEntity.ok(client.getEntityByName(name));
        return ResponseEntity.ok(EntityModel.from(service.findByName(name)));
    }

    @PostMapping()
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest upsertEntityRequest) {
    //        return ResponseEntity.status(HttpStatus.CREATED).body(client.createEntity(upsertEntityRequest));
        var newEntity = client.createEntity(upsertEntityRequest);
        var saveEntity = service.create(DataBaseEntity.from(newEntity));
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.from(saveEntity));

    }

    @PutMapping({"/{id}"})
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest upsertEntityRequest) {
    //        return ResponseEntity.status(HttpStatus.CREATED).body(client.updateEntity(id, upsertEntityRequest));
        var updateEntity = client.updateEntity(id, upsertEntityRequest);
        var updateDBEntity = service.update(id , DataBaseEntity.from(updateEntity));
        return ResponseEntity.ok(EntityModel.from(updateDBEntity));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel> deleteEntity(@PathVariable UUID id) {
    //        client.deleteEntityById(id);
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
