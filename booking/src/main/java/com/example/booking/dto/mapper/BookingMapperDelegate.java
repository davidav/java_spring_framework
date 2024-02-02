package com.example.booking.dto.mapper;

import com.example.booking.dto.booking.BookingRequest;
import com.example.booking.dto.booking.BookingResponse;
import com.example.booking.entity.Booking;
import com.example.booking.entity.Room;
import com.example.booking.service.RoomService;
import com.example.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

public abstract class BookingMapperDelegate implements BookingMapper{

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;
    @Override
    public BookingResponse bookingToResponse(Booking booking) {
        return BookingResponse.builder()
                .arrival(booking.getArrival())
                .departure(booking.getDeparture())
                .roomIds(booking.getRooms().stream().map(Room::getId).collect(Collectors.toList()))
                .userId(booking.getUser().getId())
                .build();
    }

    @Override
    public Booking requestToBooking(BookingRequest request) {
        return Booking.builder()
                .arrival(request.getArrival())
                .departure(request.getDeparture())
                .rooms(roomService.findAllByIdIn(request.getRoomIds()))
                .user(userService.findById(request.getUserId()))
                .build();
    }
}