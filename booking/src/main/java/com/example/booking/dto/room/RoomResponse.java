package com.example.booking.dto.room;

import com.example.booking.dto.booking.BookingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    private Long id;
    private String name;
    private String description;
    private Integer number;
    private Double price;
    private Integer capacity;
    private Long hotelId;

}
