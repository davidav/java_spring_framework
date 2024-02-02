package com.example.booking.dto.mapper;

import com.example.booking.dto.hotel.HotelResponse;
import com.example.booking.entity.Hotel;
import com.example.booking.entity.Room;

import java.util.stream.Collectors;

public abstract class HotelMapperDelegate implements HotelMapper{

    @Override
    public HotelResponse hotelToResponse(Hotel hotel) {

        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .title(hotel.getTitle())
                .city(hotel.getCity())
                .address(hotel.getAddress())
                .fromCentre(hotel.getFromCentre())
                .rating(hotel.getRating())
                .numberOfRatings(hotel.getNumberOfRatings())
                .roomIds(hotel.getRooms().stream().map(Room::getId).collect(Collectors.toList()))
                .build();
    }
}
