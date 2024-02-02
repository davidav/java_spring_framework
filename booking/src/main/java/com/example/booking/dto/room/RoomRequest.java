package com.example.booking.dto.room;

import lombok.*;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {

    private String name;
    private String description;
    private Integer number;
    private Double price;
    private Integer capacity;
    private Long hotelId;

}
