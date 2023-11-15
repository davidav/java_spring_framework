package com.example.rest.dto.mapper.v2;

import com.example.rest.dto.UpsertOrderRequest;
import com.example.rest.model.Order;
import com.example.rest.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrderMapperDelegate implements OrderMapperV2 {
    @Autowired
    private ClientService clientService;

    @Override
    public Order requestToOrder(UpsertOrderRequest request) {
        Order order = new Order();
        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(clientService.findById(request.getClientId()));

        return order;
    }

    @Override
    public Order requestToOrder(Long orderId, UpsertOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(orderId);
        return order;
    }
}
