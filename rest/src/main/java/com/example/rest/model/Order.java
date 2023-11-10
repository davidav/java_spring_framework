package com.example.rest.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@RequiredArgsConstructor
public class Order {
    private Long id;
    private String product;
    private BigDecimal cost;
    private Client client;
    private Instant createAt;
    private Instant updateAt;

}
