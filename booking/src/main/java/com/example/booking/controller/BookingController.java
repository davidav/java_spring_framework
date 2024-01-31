package com.example.booking.controller;

import com.example.booking.dto.booking.BookingListResponse;
import com.example.booking.dto.booking.BookingRequest;
import com.example.booking.dto.booking.BookingResponse;
import com.example.booking.dto.mapper.BookingMapper;
import com.example.booking.entity.Booking;
import com.example.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/booking")
@Slf4j
public class BookingController {

    private final BookingMapper bookingMapper;
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<BookingListResponse> getAll(){

        return ResponseEntity.ok()
                .body(bookingMapper.bookingListToBookingListResponse(bookingService.getAll()));
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request){
        log.info("BookingController -> create {}", request);
        Booking booking = bookingService.save(bookingMapper.requestToBooking(request));
        log.info("BookingController -> create after repo {}", booking);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingMapper.bookingToResponse(booking));

    }



}
