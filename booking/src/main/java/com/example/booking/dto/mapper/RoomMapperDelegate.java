package com.example.booking.dto.mapper;


import com.example.booking.dto.room.RoomRequest;
import com.example.booking.dto.room.RoomResponse;
import com.example.booking.entity.Room;
import com.example.booking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RoomMapperDelegate implements RoomMapper{

    @Autowired
    private HotelService hotelService;

    @Override
    public Room requestToRoom(RoomRequest request) {

        return Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .number(request.getNumber())
                .price(request.getPrice())
                .capacity(request.getCapacity())
                .hotel(hotelService.findById(request.getHotelId()))
                .build();
    }

    @Override
    public Room requestToRoom(Long id, RoomRequest request) {
        Room room = requestToRoom(request);
        room.setId(id);

        return room;
    }

    @Override
    public RoomResponse roomToResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .number(room.getNumber())
                .price(room.getPrice())
                .capacity(room.getCapacity())
                .hotelId(room.getHotel().getId())
                .build();
    }
}
