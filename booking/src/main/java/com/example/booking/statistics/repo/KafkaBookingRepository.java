package com.example.booking.statistics.repo;

import com.example.booking.statistics.model.KafkaBooking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface KafkaBookingRepository extends MongoRepository<KafkaBooking, UUID> {



}
