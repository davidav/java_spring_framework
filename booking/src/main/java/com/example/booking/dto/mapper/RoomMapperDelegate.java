package com.example.booking.dto.mapper;


import com.example.booking.dto.room.UpsertRoomRequest;
import com.example.booking.entity.Room;
import com.example.booking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RoomMapperDelegate implements RoomMapper{

    @Autowired
    private HotelService hotelService;

    @Override
    public Room requestToRoom(UpsertRoomRequest request) {
        Room room = Room.builder()
                .hotel(hotelService.findById(request.getHotel_id()))
                .name(request.getName())
                .description(request.getDescription())
                .number(request.getNumber())
                .capacity(request.getCapacity())
                .price(request.getPrice())
                .unavailableDates(request.getUnavailableDates())
                .build();



        return null;
    }
}
