package com.example.booking.dto.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
