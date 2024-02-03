package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Reserve;
import com.example.booking.entity.Room;
import com.example.booking.exception.BookingException;
import com.example.booking.repo.BookingRepository;
import com.example.booking.repo.ReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        Booking savedBooking = bookingRepository.save(booking);
        savedBooking.getRooms().forEach(room -> room.addBooking(savedBooking));

        return savedBooking;
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    private void checkBooked(Booking booking) {
        List<Reserve> reserves = reserveRepository.findByRoomsIdIn(
                booking.getRooms().stream()
                        .map(Room::getId)
                        .collect(Collectors.toList()));
        reserves.forEach(reserve -> {
            log.info("BookingServiceImpl -> checkBooked ");
            if (reserve.getFromDate().equals(booking.getArrival()) ||
                    reserve.getFromDate().equals(booking.getDeparture()) ||
                    reserve.getToDate().equals(booking.getArrival()) ||
                    reserve.getToDate().equals(booking.getDeparture())) {
                throw new BookingException(BOOKED);
            }
            if (booking.getArrival().isAfter(reserve.getFromDate())) {
                if (booking.getArrival().isBefore(reserve.getToDate())) {
                    throw new BookingException(BOOKED);
                }
            } else {
                if (booking.getDeparture().isAfter(reserve.getFromDate())) {
                    throw new BookingException(BOOKED);
                }
            }
        });
    }
}














