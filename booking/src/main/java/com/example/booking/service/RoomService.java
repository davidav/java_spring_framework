package com.example.booking.service;

import com.example.booking.entity.Room;


public interface RoomService {

    Room findById(Long id);

    Room save(Room room);

    Room update(Room room);

    void deleteById(Long id);

}
