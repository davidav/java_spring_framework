package com.example.booking.service;

import com.example.booking.entity.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> getAll();

    Booking save(Booking booking);

    void deleteById(Long id);
}
