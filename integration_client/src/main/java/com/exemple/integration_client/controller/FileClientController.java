package com.exemple.integration_client.controller;

import com.exemple.integration_client.clients.OkHttpClientSender;
import com.exemple.integration_client.clients.OpenFeignClient;
import com.exemple.integration_client.clients.RestTemplateClient;
import com.exemple.integration_client.clients.WebClientSender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/client/file")
@RequiredArgsConstructor
public class FileClientController {

//    private final OkHttpClientSender client;
//    private final RestTemplateClient client;
//    private final WebClientSender client;
    private final OpenFeignClient client;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file){
        client.uploadFile(file);

        return ResponseEntity.ok("File was upload");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName){
        Resource resource = client.downloadFile(fileName);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
