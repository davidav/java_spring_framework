package com.example.booking.dto.room;

import com.example.booking.entity.UnavailableDate;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpsertRoomRequest {

    private String name;
    private String description;
    private Integer number;
    private Double price;
    private Integer capacity;
    private List<UnavailableDate> unavailableDates = new ArrayList<>();

}
