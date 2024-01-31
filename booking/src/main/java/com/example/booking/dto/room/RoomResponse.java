package com.example.booking.dto.room;

import com.example.booking.entity.Hotel;
import com.example.booking.entity.UnavailableDate;
import lombok.*;
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
    private List<UnavailableDate> unavailableDates;
    private Hotel hotel;

}
