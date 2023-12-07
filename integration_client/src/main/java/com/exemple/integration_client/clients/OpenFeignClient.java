package com.exemple.integration_client.clients;

import com.exemple.integration_client.model.EntityModel;
import com.exemple.integration_client.model.UpsertEntityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "openFeignClient", url = "${app.integration.base-url}")
public interface OpenFeignClient {

    @PostMapping(value = "/api/v1/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("file") MultipartFile file);

    @GetMapping(value = "/api/v1/file/download/{fileName}")
    Resource downloadFile(@PathVariable("fileName") String fileName);

    @GetMapping(value = "/api/v1/entity")
    List<EntityModel> getList();

    @GetMapping(value = "/api/v1/entity/{name}")
    EntityModel getEntityByName(@PathVariable("name") String name);

    @PostMapping(value = "/api/v1/entity")
    EntityModel createEntity(@RequestBody UpsertEntityRequest upsertEntityRequest);

    @PutMapping(value = "/api/v1/entity/{id}")
    EntityModel updateEntity(@PathVariable("id") UUID id, @RequestBody UpsertEntityRequest upsertEntityRequest);

    @DeleteMapping(value = "/api/v1/entity/{id}")
    void deleteEntityById(@PathVariable("id") UUID id);


}
