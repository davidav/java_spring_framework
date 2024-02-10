package com.example.booking.statistics.repo;

import com.example.booking.statistics.model.KafkaUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface KafkaUserRepository extends MongoRepository<KafkaUser, UUID> {

}
