package com.example.booking.service;


import com.example.booking.dto.PagesRequest;
import com.example.booking.entity.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {

//    List<Hotel> filterBy(HotelFilter filter);

    List<Hotel> findAll(PagesRequest request);

    Hotel findById(Long id, UserDetails userDetails);

    Hotel save(Hotel hotel);

    Hotel update(Hotel hotel, UserDetails userDetails);

    void deleteById(Long id, UserDetails userDetails);

    Hotel findByLogin(String login);

}
