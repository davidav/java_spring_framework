package com.example.orderstatusservice.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
@Data
@Builder
public class StatusEvent implements Serializable {

    private String status;
    private Instant date;

}
