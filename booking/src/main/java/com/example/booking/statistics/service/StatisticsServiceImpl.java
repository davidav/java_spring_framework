package com.example.booking.statistics.service;

import com.example.booking.statistics.model.BookingStatistics;
import com.example.booking.statistics.model.KafkaBooking;
import com.example.booking.statistics.model.KafkaUser;
import com.example.booking.statistics.model.UserStatistics;
import com.example.booking.statistics.repo.KafkaBookingRepository;
import com.example.booking.statistics.repo.KafkaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{

    private final KafkaUserRepository kafkaUserRepository;
    private final KafkaBookingRepository kafkaBookingRepository;

    public void addUser(KafkaUser kafkaUser) {
        kafkaUserRepository.save(kafkaUser);
    }

    public void addBooking(KafkaBooking kafkaBooking) {
        kafkaBookingRepository.save(kafkaBooking);
    }

    public UserStatistics getUserStat() {
        return new UserStatistics();
    }

    public BookingStatistics getBookingStat() {
        return new BookingStatistics();
    }
}
