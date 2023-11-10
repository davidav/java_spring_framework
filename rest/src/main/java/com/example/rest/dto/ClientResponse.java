package com.example.rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ClientResponse {

    private Long id;
    private String name;
    private List<OrderResponse> orders = new ArrayList<>();
}
