package com.example.rest.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Client {
    private Long id;
    private String name;
    private List<Order> orders;

    public void addOrder(Order order){
        orders.add(order);
    }

    public void removeOrder(Long orderId){
        orders = orders.stream().filter(order -> !order.getId().equals(orderId)).collect(Collectors.toList());
    }

}
