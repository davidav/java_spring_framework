package com.example.orderservice.model;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class StatusEvent{
    private String status;
    private Instant date;
}