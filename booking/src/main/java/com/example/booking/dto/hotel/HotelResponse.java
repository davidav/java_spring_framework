package com.example.booking.dto.hotel;

import com.example.booking.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponse {

    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Long fromCentre;
    private Integer rating;
    private Integer numberOfRatings;
    private List<Long> room_ids;

}
