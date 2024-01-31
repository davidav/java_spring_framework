package com.example.booking.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private Instant arrivalDate;
    private Instant departureDate;
    private List<Long> room_ids = new ArrayList<>();
    private Long user_id;

}
