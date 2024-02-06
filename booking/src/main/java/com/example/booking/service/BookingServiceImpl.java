package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.Reserve;
import com.example.booking.entity.Room;
import com.example.booking.exception.BookingException;
import com.example.booking.repo.BookingRepository;
import com.example.booking.repo.ReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final String BOOKED = "Room is booked for these dates";
    private final BookingRepository bookingRepository;
    private final ReserveRepository reserveRepository;

    private final UserService userService;

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking save(Booking booking, UserDetails userDetails) {
        booking.setUser(userService.findByUsername(userDetails.getUsername()));
        checkBooked(booking);
        Room room = booking.getRoom();
        Reserve newReserve = Reserve.builder()
                .fromDate(booking.getArrival())
                .toDate(booking.getDeparture())
                .room(booking.getRoom())
                .build();
        Reserve savedReserved = reserveRepository.save(newReserve);
        room.addReserve(savedReserved);

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    private void checkBooked(Booking booking) {
        log.info("BookingServiceImpl -> checkBooked ");
        List<Reserve> existReserves = reserveRepository.findAllByRoom(booking.getRoom());
        existReserves.forEach(reserve -> {
            if (reserve.getFromDate().equals(booking.getArrival()) ||
                    reserve.getFromDate().equals(booking.getDeparture()) ||
                    reserve.getToDate().equals(booking.getArrival()) ||
                    reserve.getToDate().equals(booking.getDeparture())) {
                log.info("погран даты совпали");
                throw new BookingException(BOOKED);
            }
            if (booking.getArrival().isAfter(reserve.getFromDate())) {
                if (booking.getArrival().isBefore(reserve.getToDate())) {
                    log.info("попали в диапазон");
                    throw new BookingException(BOOKED);
                }
            } else {
                if (booking.getDeparture().isAfter(reserve.getFromDate())) {
                    log.info("гдето в диапазоне");
                    throw new BookingException(BOOKED);
                }
            }
        });

    }
}














