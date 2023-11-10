package com.example.rest.dto.mapper.v1;

import com.example.rest.dto.ClientListResponse;
import com.example.rest.dto.ClientResponse;
import com.example.rest.dto.UpsertClientRequest;
import com.example.rest.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final OrderMapper orderMapper;

    public Client requestToClient(UpsertClientRequest request){
        return Client.builder()
                .name(request.getName())
                .build();
        }

    public Client requestToClient(Long id, UpsertClientRequest request){
        Client client = requestToClient(request);
        client.setId(id);

        return client;

    }

    public ClientResponse clientToResponse(Client client){
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getName())
                .orders(orderMapper.orderListToResponseList(client.getOrders()))
                .build();
    }

    public ClientListResponse clientListToClientListResponse(List<Client> clients){
        ClientListResponse response = new ClientListResponse();
        response.setClients(
                clients.stream()
                        .map(this::clientToResponse)
                        .collect(Collectors.toList())
        );
        return response;
    }



}
