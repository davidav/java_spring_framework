package com.exemple.integration_client.clients;

import com.exemple.integration_client.model.EntityModel;
import com.exemple.integration_client.model.UpsertEntityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebClientSender {

    private final WebClient webClient;

    public void uploadFile(MultipartFile file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource())
                .filename(file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM);

        webClient.post()
                .uri("/api/v1/file/upload")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public Resource downloadFile(String fileName) {
        return webClient.get()
                .uri("/api/v1/file/download/{fileName}", fileName)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToMono(Resource.class)
                .block();
    }

    public List<EntityModel> getList() {
        return webClient.get()
                .uri("/api/v1/entity")
                .retrieve()
                .bodyToFlux(EntityModel.class)
                .collectList()
                .block();
    }

    public EntityModel getEntityByName(String name) {
        return webClient.get()
                .uri("/api/v1/entity/{name}", name)
                .retrieve()
                .bodyToMono(EntityModel.class)
                .block();
    }

    public EntityModel createEntity(UpsertEntityRequest upsertEntityRequest) {
        return webClient.post()
                .uri("/api/v1/entity")
                .bodyValue(upsertEntityRequest)
                .retrieve()
                .bodyToMono(EntityModel.class)
                .block();
    }

    public EntityModel updateEntity(UUID id, UpsertEntityRequest upsertEntityRequest) {
        return webClient.put()
                .uri("/api/v1/entity/{id}", id)
                .bodyValue(upsertEntityRequest)
                .retrieve()
                .bodyToMono(EntityModel.class)
                .block();
    }

    public void deleteEntityById(UUID id) {
        webClient.delete()
                .uri("/api/v1/entity/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
