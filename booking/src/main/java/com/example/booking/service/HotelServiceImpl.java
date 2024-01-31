package com.example.booking.service;

import com.example.booking.entity.Hotel;
import com.example.booking.repo.HotelRepository;
import com.example.booking.util.AppHelperUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        MessageFormatter.format("Hotel with id {} not found", id).getMessage()));
    }

    @Override
    public Hotel save(Hotel hotel) {
        log.info("HotelServiceImpl -> save {}", hotel);
        return hotelRepository.save(hotel);
    }

    @Override
    public Hotel update(Hotel hotel) {
        Hotel existedHotel = findById(hotel.getId());
        AppHelperUtils.copyNonNullProperties(hotel, existedHotel);
        return hotelRepository.save(existedHotel);
    }

    @Override
    public void deleteById(Long id) {

        hotelRepository.deleteById(id);

    }

    @Override
    public List<Hotel> findAll() {

        return hotelRepository.findAll();
    }
}
