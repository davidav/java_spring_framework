package com.example.orderstatusservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderEvent implements Serializable {
    private String product;
    private Integer quantity;

}
