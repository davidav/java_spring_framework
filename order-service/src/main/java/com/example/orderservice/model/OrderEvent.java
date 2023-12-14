package com.example.orderservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEvent implements Serializable {
    private String product;
    private Integer quantity;

    public static OrderEvent from(Order order){
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setProduct(order.getProduct());
        orderEvent.setQuantity(order.getQuantity());
        return orderEvent;
    }

}
