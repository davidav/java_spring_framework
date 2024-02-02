package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Reserve;
import com.example.booking.exception.BookingException;
import com.example.booking.repo.BookingRepository;
import com.example.booking.repo.ReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final String BOOKED = "Room is booked for these dates";
    private final BookingRepository bookingRepository;
    private final ReserveRepository reserveRepository;

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking save(Booking booking) {
        checkBooked(booking);
        return bookingRepository.save(booking);
    }

    private void checkBooked(Booking booking) {
        List<Reserve> reserves = reserveRepository.findByRoomsIdIn(booking.getRooms());
        reserves.forEach(reserve -> {
            log.info("BookingServiceImpl -> checkBooked ");
            if (reserve.getFromDateReserved().equals(booking.getArrival()) ||
                    reserve.getFromDateReserved().equals(booking.getDeparture()) ||
                    reserve.getToDateReserved().equals(booking.getArrival()) ||
                    reserve.getToDateReserved().equals(booking.getDeparture())) {
                throw new BookingException(BOOKED);
            }
            if (booking.getArrival().isAfter(reserve.getFromDateReserved())) {
                if (booking.getArrival().isBefore(reserve.getToDateReserved())) {
                    throw new BookingException(BOOKED);
                }
            } else {
                if (booking.getDeparture().isAfter(reserve.getFromDateReserved())){
                    throw new BookingException(BOOKED);
                }
            }
        });
    }
}














