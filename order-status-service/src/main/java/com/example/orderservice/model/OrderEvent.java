package com.example.orderservice.model;

import lombok.Data;

import java.io.Serializable;


@Data
public class OrderEvent implements Serializable {
    private String product;
    private Integer quantity;
}
