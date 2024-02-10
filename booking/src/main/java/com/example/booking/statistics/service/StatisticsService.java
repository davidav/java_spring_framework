package com.example.booking.statistics.service;

import com.example.booking.statistics.model.BookingStatistics;
import com.example.booking.statistics.model.KafkaBooking;
import com.example.booking.statistics.model.KafkaUser;
import com.example.booking.statistics.model.UserStatistics;

public interface StatisticsService {

    void addUser(KafkaUser kafkaUser);

    void addBooking(KafkaBooking kafkaBooking);

    UserStatistics getUserStat();

    BookingStatistics getBookingStat();

}
