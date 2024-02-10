package com.example.booking.statistics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "booking")
public class KafkaBooking {

    private Long userId;
    private Long roomId;
    private Instant arrival;
    private Instant departure;

}
