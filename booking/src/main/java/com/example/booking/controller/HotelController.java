package com.example.booking.controller;

import com.example.booking.dto.hotel.HotelListResponse;
import com.example.booking.dto.hotel.HotelResponse;
import com.example.booking.dto.hotel.UpsertHotelRequest;
import com.example.booking.dto.mapper.HotelMapper;
import com.example.booking.entity.Hotel;
import com.example.booking.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/hotel")
@Slf4j
public class HotelController {

    private final HotelMapper hotelMapper;
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<HotelListResponse> findAll() {
        log.info("HotelController -> findAll");

        return ResponseEntity.ok(
                hotelMapper.hotelListToHotelListResponse(hotelService.findAll()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> findById(@PathVariable Long id) {
        log.info("HotelController -> findById {}", id);
        return ResponseEntity.ok(
                hotelMapper.hotelToResponse(hotelService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid UpsertHotelRequest request) {
        log.info("HotelController -> create {}", request.getName());
        Hotel hotel = hotelService.save(hotelMapper.requestToHotel(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hotelMapper.hotelToResponse(hotel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertHotelRequest request) {
        log.info("HotelController -> update {}", id);
        Hotel hotel = hotelService.update(hotelMapper.requestToHotel(id, request));

        return ResponseEntity.ok(hotelMapper.hotelToResponse(hotel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.info("HotelController -> deleteById {}", id);
        hotelService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
