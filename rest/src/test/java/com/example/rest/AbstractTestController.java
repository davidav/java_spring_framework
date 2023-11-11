package com.example.rest;

import com.example.rest.dto.ClientResponse;
import com.example.rest.dto.OrderResponse;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class AbstractTestController {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected Client createClient(Long id, Order order){
        Client client = new Client(id,"Client", new ArrayList<>());
        if (order != null){
            order.setClient(client);
            client.addOrder(order);
        }
        return client;
    }

    protected Order createOrder(Long id, Long cost, Client client){
       return new Order(
               id,
               "Test product " + id,
               BigDecimal.valueOf(cost),
               client,
               Instant.now(),
               Instant.now()
       );
    }

    protected ClientResponse createClientResponse(Long id, OrderResponse orderResponse){
        ClientResponse clientResponse = new ClientResponse( id,"Client " + 1, new ArrayList<>());
        if (orderResponse != null){
            clientResponse.getOrders().add(orderResponse);
        }
        return clientResponse;
    }

    protected OrderResponse createOrderResponse(Long id, Long cost){
        return new OrderResponse(id, "Test product " + id, BigDecimal.valueOf(cost));
    }

}