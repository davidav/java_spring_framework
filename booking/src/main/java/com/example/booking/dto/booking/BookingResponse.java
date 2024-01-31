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
public class BookingResponse {

    private Instant arrivalDate;
    private Instant departureDate;
    private List<Long> roomIds = new ArrayList<>();
    private Long userId;


}
