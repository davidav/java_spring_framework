package com.example.booking.statistics.controller;

import com.example.booking.statistics.model.BookingStatistics;
import com.example.booking.statistics.model.KafkaBooking;
import com.example.booking.statistics.model.KafkaUser;
import com.example.booking.statistics.model.UserStatistics;
import com.example.booking.statistics.service.StatisticsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaUserTopic}")
    private String topicUserName;

    @Value("${app.kafka.kafkaBookingTopic}")
    private String topicBookingName;

    private final KafkaTemplate<String, KafkaUser> userKafkaTemplate;
    private final KafkaTemplate<String, KafkaBooking> bookingKafkaTemplate;
    private final StatisticsServiceImpl statisticsService;

    @PostMapping("/send-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sendUser(@RequestBody KafkaUser kafkaUser){
        userKafkaTemplate.send(topicUserName, kafkaUser);
        return ResponseEntity.ok("User send to kafka");
    }

    @PostMapping("/send-booking")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> sendBooking(@RequestBody KafkaBooking kafkaBooking){
        bookingKafkaTemplate.send(topicBookingName, kafkaBooking);
        return ResponseEntity.ok("Booking send to kafka");
    }

    @GetMapping("/get-users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserStatistics> getUserStat(){
        return ResponseEntity.ok(
                statisticsService.getUserStat());
    }

    @GetMapping("/get-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingStatistics> getBookingStat(){
        return ResponseEntity.ok(
                statisticsService.getBookingStat());
    }


}
