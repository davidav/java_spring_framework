package com.example.rest.dto;


import lombok.Data;
import java.util.List;


@Data
public class OrderListResponse {

    private List<OrderResponse> orders;

}
