package com.example.rest.web.controller;

import com.example.rest.AbstractTestController;
import com.example.rest.StringTestUtils;
import com.example.rest.dto.ClientListResponse;
import com.example.rest.dto.ClientResponse;
import com.example.rest.dto.OrderResponse;
import com.example.rest.dto.mapper.v1.ClientMapper;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.service.ClientService;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerTest extends AbstractTestController{

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientMapper clientMapper;

    @Test
    public void whenFindAll_thenReturnAllClients() throws Exception{
        List<Client> clients = new ArrayList<>();
        clients.add(createClient(1L, null));
        Order order = createOrder(1l, 100l, null);
        clients.add(createClient(2l, order));

        List<ClientResponse> clientResponses = new ArrayList<>();
        clientResponses.add(createClientResponse(1l,null));
        OrderResponse orderResponse = createOrderResponse(1l,100l);
        clientResponses.add(createClientResponse(2l,orderResponse));
        ClientListResponse clientListResponse = new ClientListResponse(clientResponses);

        Mockito.when(clientService.findAll()).thenReturn(clients);
        Mockito.when(clientMapper.clientListToClientListResponse(clients)).thenReturn(clientListResponse);

        String actualResponse = mockMvc.perform(get("/api/v1/client"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expectedResponse = StringTestUtils
                .readStringFromResource("response/find_all_clients_response.json");

        Mockito.verify(clientService, Mockito.times(1)).findAll();
        Mockito.verify(clientMapper, Mockito.times(1)).clientListToClientListResponse(clients);

        JsonAssert.assertJsonEquals(expectedResponse,actualResponse);



    }
}
