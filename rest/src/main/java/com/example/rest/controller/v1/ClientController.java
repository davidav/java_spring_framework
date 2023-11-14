package com.example.rest.controller.v1;

import com.example.rest.dto.ClientListResponse;
import com.example.rest.dto.ClientResponse;
import com.example.rest.dto.ErrorResponse;
import com.example.rest.dto.UpsertClientRequest;
import com.example.rest.dto.mapper.v1.ClientMapper;
import com.example.rest.model.Client;
import com.example.rest.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
@Tag(name = "Client v1", description = "Client API version V1")
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Operation(
            summary = "Get all clients",
            description = "Get all client. Return list of clients with orders",
            tags = {"client"}
    )
    @GetMapping
    public ResponseEntity<ClientListResponse> findAll() {
        return ResponseEntity.ok(
                clientMapper.clientListToClientListResponse(clientService.findAll()));
    }

    @Operation(
            summary = "Get client by id",
            description = "Get client by id. Return id, name and list of orders",
            tags = {"client", "id"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = ClientResponse.class), mediaType = "application/json")}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")}
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                clientMapper.clientToResponse(clientService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody @Valid UpsertClientRequest request) {
        Client newClient = clientService.save(clientMapper.requestToClient(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clientMapper.clientToResponse(newClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable("id") Long clientId, @RequestBody UpsertClientRequest request) {
        Client updatedClient = clientService.update(clientMapper.requestToClient(clientId, request));
        return ResponseEntity.ok(clientMapper.clientToResponse(updatedClient));
    }

    @Operation(
            summary = "Delete client by id",
            description = "Delete client by id. No Return",
            tags = {"client", "id"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
